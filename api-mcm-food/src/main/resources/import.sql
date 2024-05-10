insert into tb_cozinha (nome) values ('Tailandesa');
insert into tb_cozinha (nome) values ('Indiana');
insert into tb_cozinha (nome) values ('Brasileira');
insert into tb_cozinha (nome) values ('Mexicana');
insert into tb_cozinha (nome) values ('Italiana');
insert into tb_cozinha (nome) values ('Chinesa');
insert into tb_cozinha (nome) values ('Peruana');
insert into tb_cozinha (nome) values ('Colombiana');
insert into tb_cozinha (nome) values ('Equatoriana');

insert into tb_restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into tb_restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into tb_restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);

insert into tb_estado (nome) values ("Paraná");
insert into tb_estado (nome) values ("Ceará");
insert into tb_estado (nome) values ("Minas Gerais");
insert into tb_estado (nome) values ("Bahia");

insert into tb_cidade (nome, estado_id) values ("Curitiba", 1);
insert into tb_cidade (nome, estado_id) values ("São José dos Pinhais", 1);
insert into tb_cidade (nome, estado_id) values ("Pinhais", 1);
insert into tb_cidade (nome, estado_id) values ("Colombo", 1);
insert into tb_cidade (nome, estado_id) values ("Araucária", 1);
insert into tb_cidade (nome, estado_id) values ("Fortaleza", 2);
insert into tb_cidade (nome, estado_id) values ("Acaraú", 2);
insert into tb_cidade (nome, estado_id) values ("Diamantina", 3);
insert into tb_cidade (nome, estado_id) values ("Salvador", 4);
insert into tb_cidade (nome, estado_id) values ("Porto Seguro", 4);
insert into tb_cidade (nome, estado_id) values ("Jacobina", 4);
insert into tb_cidade (nome, estado_id) values ("Juazeiro", 4);

insert into tb_forma_pagamento (id, descricao) values (1, "Cartão de crédito");
insert into tb_forma_pagamento (id, descricao) values (2, "Cartão de débito");
insert into tb_forma_pagamento (id, descricao) values (3, "Dinheiro");

insert into tb_restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,1),(1,2), (1,3);
insert into tb_restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (2,1),(2,2);
insert into tb_restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,3);


