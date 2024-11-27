insert into tb_user (icon, email, blocked, name, password, role, type, deleted, score)
values ('icones/tatiana.JPG', 'tatiana.costa@id.uff.br', 'false', 'Tatiana Costa', '$2a$10$ldFbPwNYtHLNqNhg8lN7M.vadBTH85kTgRyW3m9YkOUzbW1SqOzmW', '2', '2', 'false', 0),
       ('icones/arthur.JPG', 'arthur.lima@id.uff.br', 'false', 'Arthur Lima', '$2a$10$mbxFlKCmQjoiWQG7.l1pyuR.Q5B0b3lUpB/sf4V7HlFeGjnLTaaHe', '1', '2', 'false', 0),
       ('icones/julia.JPG', 'julia.medeiros@id.uff.br', 'false', 'Julia Medeiros', '$2a$10$EBhF33AK6HpbRugqQZMH.eBhqX6LT0uWXTk1WLkL5ll5HGvR5CTsO', '0', '0', 'false', 0),
       ('icones/luiza.JPG', 'luiza.monteiro@id.uff.br', 'false', 'Luiza Monteiro', '$2a$10$ldFbPwNYtHLNqNhg8lN7M.vadBTH85kTgRyW3m9YkOUzbW1SqOzmW', '0', '0', 'false', 0),
       ('icones/jp.JPG', 'jp.fernandes@id.uff.br', 'false', 'João Pedro Fernandes', '$2a$10$mbxFlKCmQjoiWQG7.l1pyuR.Q5B0b3lUpB/sf4V7HlFeGjnLTaaHe', '1', '2', 'false', 0),
       ('icones/antonio.JPG', 'dutra.tony@id.uff.br', 'false', 'Antônio Dutra Vasconcelos', '$2a$10$EBhF33AK6HpbRugqQZMH.eBhqX6LT0uWXTk1WLkL5ll5HGvR5CTsO', '0', '1', 'false', 0);

insert into tb_chat (status, date_open, id, id_announcement, id_user)
values
    ('0', '2024-11-20 15:40:56.000000', '6e41c98b-0c13-4bae-942d-710efa65986c', 'c7b0ac04-dc11-439b-b4b6-3e2c6930d3be', 'tatiana.costa@id.uff.br');


insert into tb_city (id, name)
values ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','Rio de Janeiro'),
       ('0f6c0155-0bd3-46ba-9f60-a86851905565','Angras dos Reis'),
       ('2905ffd5-7976-4fd6-9f61-e19dbc19ce65','Petrópolis'),
       ('ba66c35c-46b9-4e65-95e0-a6d3c3998991','Macaé'),
       ('01150799-f6e8-4534-a348-a8f62f1ea065','Teresópolis'),
       ('6f1ab0b7-3d8b-4782-8018-926ed5e48cf5','Cabo Frio'),
       ('f2f7fd67-ee73-475a-9ab9-8641fba2e5ec','Duque de Caxias'),
       ('b294c3d2-4029-4693-8d70-eb73fa086d25','Tanguá'),
       ('2739445f-934d-4f59-8ff9-8ad19e6f76af','Campos dos Goytacazes'),
       ('6e41c98b-0c13-4bae-942d-710efa65986c','Niterói'),
       ('d311ab08-6c59-4f94-aa63-56dbe3ed3251','Rio das Ostras'),
       ('9bc05733-ceb1-4412-b3dd-13e9e02538ac','São Gonçalo'),
       ('7e3ff6e4-c06a-40ca-99e3-9c9979d5f113','Volta Redonda'),
       ('22d4ecac-71e5-4e86-9ade-9c4e926ce5d9','Nova Friburgo'),
       ('ffac3bc2-935d-41b5-8f4d-593ec5e18b11','Barra Mansa');

insert into tb_category (id, name)
values ('2905ffd5-7976-4fd6-9f61-e19dbc19ce65', 'Aluguel'),
       ('f67efd2a-01a9-4c13-9eae-034e3bb23c77','Venda'),
       ('b8153492-95a6-4312-9048-8f881f7f73d9','Aulas'),
       ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','Estágio'),
       ('0f6c0155-0bd3-46ba-9f60-a86851905565', 'Caronas e Transporte'),
       ('9e3bcf1c-f6e0-402f-a8a4-93e3dfd8b908', 'Eventos e Festas'),
       ('2aef8f9b-6e98-4829-9579-3d6f75c1e0a3', 'Atividades Acadêmicas'),
       ('b5abeb92-9c5b-47d8-88ec-2c1041f5bc3a', 'Ações Sociais'),
       ('8d5d6073-b7ff-4b1d-9156-84653b1e4e5d', 'Voluntariado'),
       ('c81b5bfc-91cf-42e7-9bc2-366769e4317b', 'Serviços Técnicos'),
       ('7f1f459d-e289-4d06-b9f5-8e7d7389a158', 'Achados e Perdidos'),
       ('d1723449-056a-44f5-b234-39680efbca82', 'Troca e Escambo'),
       ('22c2d0a1-61d2-4fa1-b60a-89e3e07f24f1', 'Equipamentos Esportivos'),
       ('4d447c02-f6fc-48d2-9178-07c9f5b1f6be', 'Comida e Bebida'),
       ('5d29e612-dc5f-48d5-9c5e-fc364cf1b08e', 'Emprego'),
       ('12a28bff-69af-4767-85bb-6e5671c2e83b', 'Grupo de Estudo'),
       ('9a4c3e3b-567b-4922-9eb8-f9d8c15f8d84', 'Projeto de Pesquisa'),
       ('6f3d1a4b-cc8e-4a19-8b6d-99b495558ce5', 'Doação'),
       ('ddbcf1bf-d54f-4176-aacb-05b11f21659d', 'Mobilidade Acadêmica'),
       ('707fbaf8-d46d-4b3b-9e1c-742849d41c14', 'Cursos e Workshops'),
       ('e37e639e-35f6-405f-89cc-67bb0dc5787b', 'Móveis'),
       ('b5a18c33-3836-4b4d-80a1-bc1e812f6d3a', 'Networking e Parcerias');

insert into tb_announcement (id, title, content, price, id_author, id_city, date, status)
values
    -- Niterói
    ('d90180e6-a156-4d65-a227-a75cef595c42', 'Quarto para Alugar', 'Alugo quarto em apartamento próximo à faculdade com preço acessível.', 850.0, 'tatiana.costa@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c', '2024-10-06 17:50:00', '0'),
    ('29fcb896-0893-4c55-841b-3a0c476dff9b', 'Aulas Particulares de Física', 'Aulas particulares de física para alunos de engenharia.', 80.0, 'tatiana.costa@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c', '2024-10-04 11:20:00', '0'),
    ('3987a4a2-2c10-468d-ae27-b4c39164f456', 'Procura-se Notebook Perdido', 'Perdi meu notebook no bloco de ciências exatas. Quem encontrou, por favor entrar em contato.', 0, 'arthur.lima@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c', '2024-10-05 21:05:00', '0'),
    ('c7b0ac04-dc11-439b-b4b6-3e2c6930d3be', 'Casa para Aluguar', 'Alugo quarto em casa próximo à faculdade com preço acessível para estudantes.', 2500.0, 'luiza.monteiro@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c','2024-09-18 10:11:00', '0'),
    ('8d9c2b8e-893b-47b3-87e1-b3f5c2d5fd62', 'Chave de Carro Encontrada no Bloco A', 'Encontrei uma chave de carro no Bloco A, próximo à entrada principal. Parece ser de um carro modelo sedã. Quem perdeu, por favor entrar em contato para recuperar.', 0, 'tatiana.costa@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c','2024-10-13 17:15:00', '0'),
    ('6f4b9f52-4a82-4fa3-9b90-f7c89e05d8d9', 'Mentoria para Novos Alunos de Engenharia', 'Ofereço mentoria para novos alunos de Engenharia que buscam orientação sobre o curso e dicas para o início da vida acadêmica. Encontros semanais e dicas de estudo.', 0, 'luiza.monteiro@id.uff.br', '6e41c98b-0c13-4bae-942d-710efa65986c','2024-10-12 11:00:00', '0'),

    -- São Gonçalo
    ('87ae5802-d813-48b5-98b9-2c26f6a1a333', 'Coaching de Carreira para Alunos de Economia', 'Sessões de coaching para alunos de Economia interessados em planejamento de carreira e desenvolvimento profissional. Aulas quinzenais com foco em networking e habilidades específicas do setor.', 0, 'luiza.monteiro@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-10-30 11:45:00', '0'),
    ('8a63df5e-68c6-41a2-9314-58e9a4e02cfc', 'Estágio em Desenvolvimento de Software', 'Vaga de estágio para estudantes de Ciência da Computação ou áreas correlatas. Atuação em desenvolvimento de software com tecnologias modernas e ambiente colaborativo.', 0, 'luiza.monteiro@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-10-29 12:50:00', '0'),
    ('f94f7315-4ff4-4ae6-8e7a-2b6e06cc2858', 'Venda de Livro de Programação', 'Vendo livro ''Introdução à Programação em Java''. Material novíssimo, utilizado apenas uma vez. Excelente para quem quer aprender a programar.', 0, 'luiza.monteiro@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-09-18 16:15:00', '0'),
    ('a8648c4c-7a31-4c79-bebe-ab5ca149e0f4', 'Doação de Móveis Usados', 'Estou doando móveis usados, incluindo uma mesa de jantar e cadeiras. Todos em bom estado. Ideal para quem está mudando de casa.', 0, 'luiza.monteiro@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-10-16 14:45:00', '0'),
    ('e5219417-14de-4f83-9e31-c2477df1024a', 'Quarto para Alugar', 'Alugo quarto em apartamento com preço acessível.', 650.0, 'jp.fernandes@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-09-06 20:00:00', '0'),
    ('e8c71d30-02b4-4db2-9e54-0cb36776f743', 'Festa de Aniversário na República', 'Venha celebrar meu aniversário com muita música e diversão! A festa será na república, com início às 18h. Traga sua bebida favorita!', 0, 'jp.fernandes@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-10-05 07:50:00', '0'),
    ('a432e2ec-1989-4e73-aad7-7d0d59ec32ec', 'Curso de Desenvolvimento Web Completo', 'Aprenda a criar websites do zero! O curso abrange HTML, CSS, JavaScript e frameworks modernos. Inscrições abertas.', 299.10, 'jp.fernandes@id.uff.br', '9bc05733-ceb1-4412-b3dd-13e9e02538ac','2024-10-14 06:40:00', '0'),

    -- Rio das Ostras
    ('d7a0bde6-2585-4787-9e5e-ef3af43b96b3', 'Aulas Particulares de Programação', 'Aulas particulares para alunos de computação.', 60.0, 'julia.medeiros@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-10-07 12:22:00', '0'),
    ('96c28a13-fd6e-4358-a2ae-54bc2c7873ef', 'Celular Encontrado no Auditório Central', 'Celular encontrado no Auditório Central durante o evento de tecnologia. Trata-se de um smartphone preto. Caso seja o dono, favor descrever a capinha para confirmar.', 0, 'julia.medeiros@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-08-19 01:41:00', '0'),
    ('92f14b73-0ccf-4b1f-92b5-0f634d5a7c15', 'Estágio em Marketing Digital', 'Buscamos estagiário para apoiar nas estratégias de marketing digital. Ideal para estudantes de Publicidade, Marketing e áreas afins, com interesse em redes sociais e campanhas digitais.', 0, 'arthur.lima@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-10-08 00:13:00', '0'),
    ('e94592b6-d63e-4638-836f-cd23cf16e41b', 'Venda de Bicicleta Mountain Bike', 'Bicicleta mountain bike em ótimo estado, ideal para trilhas e passeios. Vendo por motivo de mudança. Acompanha acessórios como capacete e suporte para garrafa.', 0, 'arthur.lima@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-08-06 22:25:00', '0'),
    ('4f203eb3-d01d-4dd0-b711-7ec8c35d6d4e', 'Doação de Livros de Literatura', 'Doação de livros de literatura em ótimo estado. Inclui clássicos e contemporâneos. Ótima oportunidade para quem ama ler!', 0, 'arthur.lima@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-10-15 18:00:00', '0'),
    ('b3ef3d9e-4b5a-4f1b-94bb-0f4eabb1da6d', 'Troca de Livros', 'Troco livros de ficção por livros de não-ficção. Se você tiver algo interessante, vamos conversar!', 0, 'julia.medeiros@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-10-25 14:54:00', '0'),
    ('aeb05dbe-6b68-4a7f-9ab3-b5f7e2e94e4c', 'Troca de Equipamentos de Camping', 'Troco uma barraca de camping em ótimo estado por uma mochila de trekking. Se interessar, entre em contato!', 0, 'julia.medeiros@id.uff.br', 'd311ab08-6c59-4f94-aa63-56dbe3ed3251','2024-08-14 12:33:00', '0'),

    -- Rio de Janeiro
    ('c5cf3a0c-4e07-4c80-9ec4-8e69a1980a96', 'Estágio em Engenharia Civil', 'Oportunidade de estágio para estudantes de Engenharia Civil. Atuação em obras e planejamento, com possibilidade de aprendizado prático no setor.', 0, 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-10-21 11:45:00', '0'),
    ('7f5f7e73-94e6-4bc1-a00b-819b4ef9c171', 'Doação de Roupas Infantis', 'Estou doando roupas infantis em bom estado, tamanho 3 a 6 anos. Se alguém precisar, entre em contato.', 0, 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-04-18 14:11:00', '0'),
    ('c90f5b68-bf69-4ac3-a80b-fd02864d2642', 'Festa Junina da Faculdade', 'Participe da nossa festa junina! Haverá comidas típicas, danças e muita diversão. Data: 15 de junho. Todos estão convidados!', 0, 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-07-16 02:02:00', '0'),
    ('b238154e-0a6b-4e74-849a-1e9ebfd24c72', 'Serviços de Design Gráfico', 'Freelancer oferece serviços de design gráfico para criação de logotipos e banners.', 0, 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-10-06 06:14:00', '0'),
    ('4a60be77-b93f-4e2e-bb8e-76b9c53c4d90', 'Pesquisa sobre Sustentabilidade em Universidades', 'Procuro colaboradores para projeto de pesquisa que analisa práticas sustentáveis em universidades. Interesse em meio ambiente é um diferencial.', 0, 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-10-07 08:25:00', '0'),
    ('7f23de70-6ae1-4657-b3f7-67a6fcd45830', 'Festa de Formatura', 'Convite para a festa de formatura da turma de Engenharia! Venha celebrar conosco, a festa será no salão de festas da universidade. Ingressos limitados!', 0, 'julia.medeiros@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-05-04 07:38:00', '0'),
    ('f3e9a8c4-1f67-41f8-b3f1-0983f0b4b197', 'Workshop de Fotografia Digital', 'Participe do nosso workshop de fotografia digital e aprenda técnicas para capturar imagens impressionantes. Vagas limitadas!', 150.90, 'luiza.monteiro@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-01-21 15:39:00', '0'),
    ('e4b5d4f2-4ac1-4b43-8b9d-e1b9d8ddefb5', 'Curso de Marketing Digital para Iniciantes', 'Entenda como o marketing digital pode ajudar seu negócio. O curso aborda SEO, redes sociais e campanhas online.', 0, 'luiza.monteiro@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-06-23 16:47:00', '0'),
    ('c8e682c8-bbba-429e-b5f3-e743c2e8e3c7', 'Oficina de Criatividade e Inovação', 'Desperte sua criatividade em nossa oficina interativa. Atividades práticas e dinâmicas para estimular a inovação.', 120.0, 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-10-14 14:04:00', '0'),
    ('db1a8f76-5b38-490c-a5c4-8f484d39e9da', 'Curso de Língua Estrangeira: Inglês para Negócios', 'Melhore suas habilidades em inglês com foco em ambientes de negócios. Aulas práticas e conversação.', 450.10, 'tatiana.costa@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-10-15 12:22:00', '0'),
    ('367d10c3-2362-4879-9e8d-162a31398536', 'Aulas de Matemática', 'Aulas em grupo para alunos.', 50.0, 'jp.fernandes@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-08-14 13:40:00', '0'),
    ('b07d028f-e4c6-4e1e-b37f-6828f2255c12', 'Análise de Dados sobre Comportamento do Consumidor', 'Estamos recrutando alunos para projeto de pesquisa que analisa dados sobre comportamento do consumidor em produtos sustentáveis. Conhecimento em análise de dados é desejável.', 0, 'jp.fernandes@id.uff.br', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4','2024-04-14 17:20:00', '0'),

    -- Barra Mansa
    ('d6c928a2-4bc8-463b-a2d8-244c5e1d1ea3', 'Doação de Brinquedos', 'Doando brinquedos em bom estado para crianças. Se você conhece alguém que possa precisar, entre em contato!', 0, 'jp.fernandes@id.uff.br', 'ffac3bc2-935d-41b5-8f4d-593ec5e18b11','2024-10-14 17:00:00', '0'),

    -- Duque de Caxias
    ('1d22330c-76f8-4c1b-8db9-8bbf7aabf756', 'Vaga de Estágio em Desenvolvimento Web', 'Estamos em busca de um estagiário para a nossa equipe de desenvolvimento web. Se você é apaixonado por tecnologia e deseja aprender, venha fazer parte do nosso time!', 0, 'dutra.tony@id.uff.br', 'f2f7fd67-ee73-475a-9ab9-8641fba2e5ec','2024-10-25 14:45:00', '0'),

    -- Macaé
    ('4937b15c-d46e-4a9f-a3fc-205cbf4867b4', 'Analista de Marketing Digital', 'Vaga disponível para analista de marketing digital com experiência em gestão de campanhas. Envie seu currículo e venha fazer parte do nosso time!', 0, 'dutra.tony@id.uff.br', 'ba66c35c-46b9-4e65-95e0-a6d3c3998991','2024-10-19 11:14:00', '0'),
    ('be8d2ef2-8914-4fc6-8610-e3cd86e65007', 'Vaga para Designer Gráfico', 'Estamos à procura de um designer gráfico criativo para se juntar à nossa equipe. É desejável ter portfólio e experiência com design digital.', 0, 'dutra.tony@id.uff.br', 'ba66c35c-46b9-4e65-95e0-a6d3c3998991','2024-10-24 12:11:00', '0'),

    -- Volta Redonda
    ('c1ab93a1-2300-4ed0-90ff-700a445c0912', 'Estudo sobre Impacto da Tecnologia na Educação', 'Busco estudantes para ajudar em pesquisa sobre o impacto das tecnologias digitais no aprendizado dos alunos. Experiência em educação é bem-vinda.', 0, 'dutra.tony@id.uff.br', '7e3ff6e4-c06a-40ca-99e3-9c9979d5f113','2024-10-15 07:15:00', '0'),
    ('f3fbc9bb-4ff3-40fa-a548-8a0f4f84f35e', 'Desenvolvedor Java Sênior', 'Estamos contratando um desenvolvedor Java sênior para trabalhar em projetos desafiadores. É necessário ter experiência em Spring Boot e Microservices.', 0, 'dutra.tony@id.uff.br', '7e3ff6e4-c06a-40ca-99e3-9c9979d5f113','2024-10-5 09:30:00', '0');

insert into tb_announcement_category (id_announcement, id_category)
values
    -- Aluguel
    ('d90180e6-a156-4d65-a227-a75cef595c42', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65'),
    ('c7b0ac04-dc11-439b-b4b6-3e2c6930d3be', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65'),
    ('e5219417-14de-4f83-9e31-c2477df1024a', '2905ffd5-7976-4fd6-9f61-e19dbc19ce65'),

    -- Aulas
    ('29fcb896-0893-4c55-841b-3a0c476dff9b', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('d7a0bde6-2585-4787-9e5e-ef3af43b96b3', 'b8153492-95a6-4312-9048-8f881f7f73d9'),
    ('367d10c3-2362-4879-9e8d-162a31398536', 'b8153492-95a6-4312-9048-8f881f7f73d9'),

    -- Mentoria e Coaching
    ('6f4b9f52-4a82-4fa3-9b90-f7c89e05d8d9', 'e37e639e-35f6-405f-89cc-67bb0dc5787b'),
    ('87ae5802-d813-48b5-98b9-2c26f6a1a333', 'e37e639e-35f6-405f-89cc-67bb0dc5787b'),

    -- Achados e Perdidos
    ('8d9c2b8e-893b-47b3-87e1-b3f5c2d5fd62', '7f1f459d-e289-4d06-b9f5-8e7d7389a158'),
    ('3987a4a2-2c10-468d-ae27-b4c39164f456', '7f1f459d-e289-4d06-b9f5-8e7d7389a158'),
    ('96c28a13-fd6e-4358-a2ae-54bc2c7873ef', '7f1f459d-e289-4d06-b9f5-8e7d7389a158'),

    -- Estágio
    ('8a63df5e-68c6-41a2-9314-58e9a4e02cfc', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4'),
    ('92f14b73-0ccf-4b1f-92b5-0f634d5a7c15', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4'),
    ('c5cf3a0c-4e07-4c80-9ec4-8e69a1980a96', 'a8648c4c-7a31-4c79-bebe-ab5ca149e0f4'),

    -- Serviços Técnicos
    ('b238154e-0a6b-4e74-849a-1e9ebfd24c72', 'c81b5bfc-91cf-42e7-9bc2-366769e4317b'),

    -- Venda
    ('e94592b6-d63e-4638-836f-cd23cf16e41b', 'f67efd2a-01a9-4c13-9eae-034e3bb23c77'),
    ('f94f7315-4ff4-4ae6-8e7a-2b6e06cc2858', 'f67efd2a-01a9-4c13-9eae-034e3bb23c77'),

    -- Doações
    ('f94f7315-4ff4-4ae6-8e7a-2b6e06cc2858', '6f3d1a4b-cc8e-4a19-8b6d-99b495558ce5'),
    ('4f203eb3-d01d-4dd0-b711-7ec8c35d6d4e', '6f3d1a4b-cc8e-4a19-8b6d-99b495558ce5'),
    ('7f5f7e73-94e6-4bc1-a00b-819b4ef9c171', '6f3d1a4b-cc8e-4a19-8b6d-99b495558ce5'),
    ('d6c928a2-4bc8-463b-a2d8-244c5e1d1ea3', '6f3d1a4b-cc8e-4a19-8b6d-99b495558ce5'),

    -- Moveis
    ('f94f7315-4ff4-4ae6-8e7a-2b6e06cc2858', 'e37e639e-35f6-405f-89cc-67bb0dc5787b'),

    -- Troca e Escambo
    ('b3ef3d9e-4b5a-4f1b-94bb-0f4eabb1da6d', 'd1723449-056a-44f5-b234-39680efbca82'),
    ('aeb05dbe-6b68-4a7f-9ab3-b5f7e2e94e4c', 'd1723449-056a-44f5-b234-39680efbca82'),

    -- Equipamento Esportivos
    ('e94592b6-d63e-4638-836f-cd23cf16e41b', '22c2d0a1-61d2-4fa1-b60a-89e3e07f24f1'),

    -- Projeto de Pesquisa
    ('4a60be77-b93f-4e2e-bb8e-76b9c53c4d90', '9a4c3e3b-567b-4922-9eb8-f9d8c15f8d84'),
    ('c1ab93a1-2300-4ed0-90ff-700a445c0912', '9a4c3e3b-567b-4922-9eb8-f9d8c15f8d84'),
    ('b07d028f-e4c6-4e1e-b37f-6828f2255c12', '9a4c3e3b-567b-4922-9eb8-f9d8c15f8d84'),

    -- Eventos e Festa
    ('e8c71d30-02b4-4db2-9e54-0cb36776f743', '9e3bcf1c-f6e0-402f-a8a4-93e3dfd8b908'),
    ('c90f5b68-bf69-4ac3-a80b-fd02864d2642', '9e3bcf1c-f6e0-402f-a8a4-93e3dfd8b908'),
    ('7f23de70-6ae1-4657-b3f7-67a6fcd45830', '9e3bcf1c-f6e0-402f-a8a4-93e3dfd8b908'),

    -- Oportunidade de Emprego
    ('1d22330c-76f8-4c1b-8db9-8bbf7aabf756', '5d29e612-dc5f-48d5-9c5e-fc364cf1b08e'),
    ('4937b15c-d46e-4a9f-a3fc-205cbf4867b4', '5d29e612-dc5f-48d5-9c5e-fc364cf1b08e'),
    ('f3fbc9bb-4ff3-40fa-a548-8a0f4f84f35e', '5d29e612-dc5f-48d5-9c5e-fc364cf1b08e'),
    ('be8d2ef2-8914-4fc6-8610-e3cd86e65007', '5d29e612-dc5f-48d5-9c5e-fc364cf1b08e'),

    -- Cursos e Workshops
    ('a432e2ec-1989-4e73-aad7-7d0d59ec32ec', '707fbaf8-d46d-4b3b-9e1c-742849d41c14'),
    ('f3e9a8c4-1f67-41f8-b3f1-0983f0b4b197', '707fbaf8-d46d-4b3b-9e1c-742849d41c14'),
    ('e4b5d4f2-4ac1-4b43-8b9d-e1b9d8ddefb5', '707fbaf8-d46d-4b3b-9e1c-742849d41c14'),
    ('c8e682c8-bbba-429e-b5f3-e743c2e8e3c7', '707fbaf8-d46d-4b3b-9e1c-742849d41c14'),
    ('db1a8f76-5b38-490c-a5c4-8f484d39e9da', '707fbaf8-d46d-4b3b-9e1c-742849d41c14');