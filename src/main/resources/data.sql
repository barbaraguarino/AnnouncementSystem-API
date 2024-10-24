insert into tb_user
    (email, blocked, name, password, role, type, deleted)
values ('tatiana.costa@id.uff.br', 'false', 'Tatiana Costa', '$2a$10$ldFbPwNYtHLNqNhg8lN7M.vadBTH85kTgRyW3m9YkOUzbW1SqOzmW', '1', '2', 'false'),
       ('arthur.lima@id.uff.br', 'false', 'Arthur Lima', '$2a$10$mbxFlKCmQjoiWQG7.l1pyuR.Q5B0b3lUpB/sf4V7HlFeGjnLTaaHe', '0', '1', 'false'),
       ('Julia Medeiros', 'false', 'Julia Medeiros', '$2a$10$EBhF33AK6HpbRugqQZMH.eBhqX6LT0uWXTk1WLkL5ll5HGvR5CTsO', '0', '0', 'false')
ON CONFLICT (email) DO NOTHING;

insert into tb_city
(id, name)
values ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','Niterói'),
       ('0f6c0155-0bd3-46ba-9f60-a86851905565','Rio das Ostras')
ON CONFLICT (name) DO NOTHING;

insert into tb_category
(id, name)
values ('2905ffd5-7976-4fd6-9f61-e19dbc19ce65', 'Desapega'),
       ('f67efd2a-01a9-4c13-9eae-034e3bb23c77','Venda'),
       ('b8153492-95a6-4312-9048-8f881f7f73d9','Aulas'),
       ('e5219417-14de-4f83-9e31-c2477df1024a','Móveis'),
       ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','Estágio'),
       ('0f6c0155-0bd3-46ba-9f60-a86851905565', 'Aluguel')
ON CONFLICT (name) DO NOTHING;