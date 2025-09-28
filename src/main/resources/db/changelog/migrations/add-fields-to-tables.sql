INSERT INTO address (city, house, street)
VALUES ('Novosibirsk', 'Pr. Lenina', '255'),
       ('Barnaul', 'Pr. Krasnoarmeisky', '10');

INSERT INTO cas_profile (full_name, address_id)
VALUES ('Cassandra_1', 1),
       ('Cassandra_2', 2);

INSERT INTO cas (name, password, profile_id)
VALUES ('cas_1', 'pass1', 1),
       ('cas_2', 'pass2', 2);