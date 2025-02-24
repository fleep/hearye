
# Architecture

## General Flow
Here we use email as it's the simplest:
```mermaid
sequenceDiagram
    box gray Client Code
        participant Clients
        participant Consumers as Client Consumer Group
    end
    participant HearYe
    participant HearYe DB
    box gray Client Infrastructure
        participant LDAP as LDAP / AD
        participant Mail Server
    end
    Clients -->> HearYe: POST /emailRequest - produce topic "enqueue-request"
    HearYe -->> HearYe DB: insert request
    loop Each recipient
        HearYe -->> HearYe: produce topic "expand-request"
        HearYe ->> LDAP: resolve group members
        HearYe ->> HearYe DB: append notifications
    end
    loop Each notification
        HearYe -->> Consumers: topic "enqueue-notification"
        Consumers -->> HearYe: produce topic "dispatch-notification"
    end
    loop Each notification
        HearYe -->> HearYe: produce topic "dispatch-notification"
        HearYe -->> Mail Server: send email
    end
    note over HearYe: 1:N expansion of requests to<br /> notifications per recipient
    LDAP ->> HearYe: resolve group members
    note over HearYe: create base notifications
    HearYe ->> HearYe DB: append notifications
    loop Each notification
        HearYe -->> Consumers: topic "enqueue-notification"
        Consumers -->> HearYe: produce topic "dispatch-notification"
    end
    loop Each notification
        alt Modification mode?
            HearYe -->> Consumers: topic modify-notification
            note over Consumers: message interpolation<br />modify recipients<br />etc.
            Consumers -->> HearYe: PATCH /email/<notificationId>
            HearYe ->> HearYe DB: append updated notification
            Consumers -->> HearYe: produce topic dispatch-notification
        else
            HearYe -->> HearYe: produce topic dispatch-notification
        end
        note over HearYe: append updated notification<br />inc dispatch attempt
        HearYe -->> Mail Server: send email
        note over HearYe: append updated notification<br />>inc dispatch result
    end
    
```

### Summary
- **Request notifications**: Clients enqueue notifications to be sent to one or more recipients
- **Notification expansion**: HearYe expands the request into individual notifications, one per 
    recipient. It should support LDAP/AD group expansion to individual recipients.
- **Modification mode**: If the Client requested the ability to modify these notifications, 
    they can listen to a topic. This allows clients make domain-specific modifications that are out of HearYe's scope:
  - **Templating**: string replacement on variables in the message body or subject 
    (their initial request may only include a base template or no message body at all). This allows client code to:
    - Integrate data from other sources
    - Apply domain specific logic to notification bodies
    - Customize messages to each recipient based on their role or other attributes
  - **Recipient modification**: add or remove recipients. Example use cases:
    - CC employee's managers on particular notifications
    - CC a salesperson's support staff on critical messages
    - Forward technical escalations to assigned roles in ticketing system
  - Importantly, modification mode allows clients to colocate their notification logic in one
    set of classes in their application's code base, rather than the transactional points in their applicaiton where 
    a notification might be sent.
  - This allows allows HearYe to be agnostic of domain-specific issues and code, and effectively acts
    allows client code to modify the HearYe pipeline.
- **Immediate mode**: for simpler use cases, clients can opt out of modification mode and send the email as formed in the original client request.
- **Request dispatch**: HearYe handles scaling, retrying, and rate limiting of notifications being sent.