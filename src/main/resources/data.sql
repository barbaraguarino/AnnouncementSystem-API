insert into tb_user
    (email, blocked, name, password, role, type)
values ('tatiana.costa@id.uff.br', 'false', 'Tatiana Costa', '$2a$10$ldFbPwNYtHLNqNhg8lN7M.vadBTH85kTgRyW3m9YkOUzbW1SqOzmW', '1', '2'),
       ('arthur.lima@id.uff.br', 'false', 'Arthur Lima', '$2a$10$mbxFlKCmQjoiWQG7.l1pyuR.Q5B0b3lUpB/sf4V7HlFeGjnLTaaHe', '0', '1'),
       ('Julia Medeiros', 'false', 'Julia Medeiros', '$2a$10$EBhF33AK6HpbRugqQZMH.eBhqX6LT0uWXTk1WLkL5ll5HGvR5CTsO', '0', '0')
ON CONFLICT (email) DO NOTHING;