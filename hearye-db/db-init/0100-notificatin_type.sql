DROP TABLE IF EXISTS notification_type;
CREATE TABLE notification_type (
    id BIGSERIAL PRIMARY KEY,
    name varchar(100)
);

INSERT INTO
    notification_type (id, name)
VALUES
    (1, 'Value 1'),
    (2, 'Value 2'),
    (3, 'Value 3'),
    (4, 'Value 4')
;
ALTER SEQUENCE notification_type_id_seq RESTART WITH 1000;