

insert into estado (id, nome) values (1, 'Paraná');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Pará');

insert into cidade (id, nome, estado_id) values (1, 'Londrina', 1);
insert into cidade (id, nome, estado_id) values (2, 'Maringa', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Osasco', 2);
insert into cidade (id, nome, estado_id) values (5, 'Santarém', 3);

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (1, 'Tai Food', 12.5, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Porto Belo Food', 9.50, 4, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Mascotinho', 5.9, 4, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (4, 'Carnes Assadas', 0, 3, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (5, 'Massas Bless', 15.50, 2, utc_timestamp, utc_timestamp);


insert into forma_pagamento (id, descricao) values (1, 'Cartão de credito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de debito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHA', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHA', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 3);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3, 1);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 2);
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (4, 1);


insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional italiano com cobertura de alho', 21, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 4);