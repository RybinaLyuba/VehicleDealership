WITH data(id,name) as(
    VALUES
        (1,'sold'),
        (2,'in stock'),
        (3,'reserved')
)
INSERT INTO status(id,name)
SELECT d.id, d.name
FROM data d
WHERE NOT EXISTS(SELECT * FROM status);

WITH data(id,name) as(
    VALUES
    (1,'supercar'),
    (2,'jet'),
    (3,'ship'),
    (4,'helicopter')
)
INSERT INTO vehicle_type(id,name)
SELECT d.id, d.name
FROM data d
WHERE NOT EXISTS(SELECT * FROM vehicle_type);