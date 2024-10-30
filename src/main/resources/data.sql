insert into tb_user
    (email, blocked, name, password, role, type, deleted)
values ('tatiana.costa@id.uff.br', 'false', 'Tatiana Costa', '$2a$10$ldFbPwNYtHLNqNhg8lN7M.vadBTH85kTgRyW3m9YkOUzbW1SqOzmW', '1', '2', 'false'),
       ('arthur.lima@id.uff.br', 'false', 'Arthur Lima', '$2a$10$mbxFlKCmQjoiWQG7.l1pyuR.Q5B0b3lUpB/sf4V7HlFeGjnLTaaHe', '0', '1', 'false'),
       ('julia.medeiros@id.uff.br', 'false', 'Julia Medeiros', '$2a$10$EBhF33AK6HpbRugqQZMH.eBhqX6LT0uWXTk1WLkL5ll5HGvR5CTsO', '0', '0', 'false')
ON CONFLICT (email) DO NOTHING;

insert into tb_city
(id, name)
values ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','São Paulo'),
       ('0f6c0155-0bd3-46ba-9f60-a86851905565','Santos'),
        ('2905ffd5-7976-4fd6-9f61-e19dbc19ce65','Paulina')
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

insert into tb_announcement (id, title, content, id_author, id_city, date, price, status)
values
    ('0f6c0155-0bd3-46ba-9f60-a86851905565', 'Apartamento com Vista para o Mar', 'Lindo apartamento de 3 quartos em Santos.', 'tatiana.costa@id.uff.br', '0f6c0155-0bd3-46ba-9f60-a86851905565', '2024-10-30 10:00:00', 450000.0, '1'),
    ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', 'Vaga de Estágio em Marketing', 'Estágio em marketing digital com possibilidade de efetivação.', 'arthur.lima@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 11:00:00', 1200.0, '1'),
    ('2905ffd5-7976-4fd6-9f61-e19dbc19ce65', 'Aula Particular de Matemática', 'Aulas de matemática para o ensino médio, presencial ou online.', 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 12:00:00', 80.0, '1'),
    ('e5219417-14de-4f83-9e31-c2477df1024a', 'Móveis de Escritório Usados', 'Conjunto de móveis para escritório, ótimo estado.', 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 13:00:00', 500.0, '1'),
    ('b8153492-95a6-4312-9048-8f881f7f73d9', 'Cama de Casal', 'Cama de casal com colchão, em perfeito estado.', 'arthur.lima@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 14:00:00', 1500.0, '1'),
    ('abcefec7-29b3-4c8f-bdf1-58d8a6c6f3d1', 'Aulas de Física', 'Aulas particulares de Física, todos os níveis.', 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 15:00:00', 70.0, '1'),
    ('fd0566b8-823e-4d92-b10c-80c916ee4c5c', 'Venda de Bicicleta', 'Bicicleta usada em ótimo estado, ideal para o dia a dia.', 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 16:00:00', 800.0, '1'),
    ('9f99d9c2-0c72-47b3-bf34-418c2393e716', 'Curso de Inglês Online', 'Curso de inglês online com professor nativo.', 'arthur.lima@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 17:00:00', 300.0, '1'),
    ('e5d77d0d-e5f8-4f9f-bf4a-1555aabe0d94', 'Estágio em Desenvolvimento', 'Vaga de estágio em desenvolvimento de software.', 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 18:00:00', 1500.0, '1'),
    ('6e58cb0c-02a3-4966-89ae-ecf9a104d1a0', 'Quarto para Alugar', 'Alugo quarto em apartamento compartilhado, perto da faculdade.', 'tatiana.costa@id.uff.br', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65', '2024-10-30 19:00:00', 600.0, '1'),
    ('e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05', 'Apartamento em São Paulo', 'Apartamento bem localizado no centro, perto de tudo.', 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 14:00:00', '0','1'),
    ('c3b69e3c-58e1-4db1-9112-e6c12357e4b6', 'Vaga de Estágio', 'Oportunidade de estágio em marketing, horário flexível.', 'arthur.lima@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', '2024-10-30 17:00:00', '0','1'),
    ('4f43aa6b-0197-4c74-b3bb-feb57c1b0a7b', 'Aula Particular de Matemática', 'Aulas particulares de matemática, todas as idades.', 'julia.medeiros@id.uff.br', '0f6c0155-0bd3-46ba-9f60-a86851905565', '2024-10-30 17:00:00', 0,'1'),
    ('d05ff98d-19b6-4a08-bae4-6e75f4c7c4a2', 'Móveis de Escritório à Venda', 'Venda de móveis de escritório usados, ótimo estado.', 'tatiana.costa@id.uff.br', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65', '2024-10-30 17:00:00', 0,'1'),
    ('b69d4f30-04a3-4e52-b6da-1c88cd07a0e6', 'Cama de Casal em Bom Estado', 'Cama de casal à venda, em ótimo estado, quase nova.', 'arthur.lima@id.uff.br', '0f6c0155-0bd3-46ba-9f60-a86851905565', '2024-10-30 17:00:00', 0,'1')
ON CONFLICT (id) DO NOTHING;

insert into tb_announcement_category (id_announcement, id_category)
values
    ('e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65'),
    ('e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05', 'f67efd2a-01a9-4c13-9eae-034e3bb23c77'),
    ('c3b69e3c-58e1-4db1-9112-e6c12357e4b6', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4'),
    ('4f43aa6b-0197-4c74-b3bb-feb57c1b0a7b', 'e5219417-14de-4f83-9e31-c2477df1024a'),
    ('b69d4f30-04a3-4e52-b6da-1c88cd07a0e6', 'e5219417-14de-4f83-9e31-c2477df1024a'),
    ('e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05', 'f67efd2a-01a9-4c13-9eae-034e3bb23c77'),
    ('e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('c3b69e3c-58e1-4db1-9112-e6c12357e4b6', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4'),
    ('4f43aa6b-0197-4c74-b3bb-feb57c1b0a7b', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('d05ff98d-19b6-4a08-bae4-6e75f4c7c4a2', 'e5219417-14de-4f83-9e31-c2477df1024a'),
    ('b69d4f30-04a3-4e52-b6da-1c88cd07a0e6', 'f67efd2a-01a9-4c13-9eae-034e3bb23c77'),
    ('abcefec7-29b3-4c8f-bdf1-58d8a6c6f3d1', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('fd0566b8-823e-4d92-b10c-80c916ee4c5c', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65'),
    ('9f99d9c2-0c72-47b3-bf34-418c2393e716', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('6e58cb0c-02a3-4966-89ae-ecf9a104d1a0', '0f6c0155-0bd3-46ba-9f60-a86851905565')
ON CONFLICT (id_announcement, id_category) DO NOTHING;

insert into tb_file (id, path, id_announcement)
values
    ('2a3d8c12-1234-4f63-b7de-2b1f4a3f5e8b', '/files/apartamento_sao_paulo.jpg', 'e9347c6e-f1f0-4d4b-a3a2-1b785b5f6d05'),
    ('8b1c4e36-56f0-4eb4-8e9e-b43d7b5e3a9f', '/files/vaga_estagio.jpg', 'c3b69e3c-58e1-4db1-9112-e6c12357e4b6'),
    ('4c2a1e15-fc1b-4af0-bd4e-1f1c82a6f4e5', '/files/aula_matematica.jpg', '4f43aa6b-0197-4c74-b3bb-feb57c1b0a7b'),
    ('d85c64e3-14f0-4e38-99af-6575288e21d9', '/files/moveis_escritorio.jpg', 'd05ff98d-19b6-4a08-bae4-6e75f4c7c4a2'),
    ('a6f50c94-7634-4af8-abc3-2465c2bba5c2', '/files/cama_casal.jpg', 'b69d4f30-04a3-4e52-b6da-1c88cd07a0e6')
ON CONFLICT (id) DO NOTHING;