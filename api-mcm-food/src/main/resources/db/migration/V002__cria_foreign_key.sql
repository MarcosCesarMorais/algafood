alter table tb_grupo_permissao add constraint fk_tb_grupo_permissao_tb_permissao
foreign key (permissao_id) references tb_permissao (id);

alter table tb_grupo_permissao add constraint fk_tb_grupo_permissao_tb_grupo
foreign key (grupo_id) references tb_grupo (id);

alter table tb_produto add constraint fk_tb_produto_tb_restaurante
foreign key (restaurante_id) references tb_restaurante (id);

alter table tb_restaurante add constraint fk_tb_restaurante_tb_cozinha
foreign key (cozinha_id) references tb_cozinha (id);

alter table tb_restaurante add constraint fk_tb_restaurante_tb_cidade
foreign key (endereco_cidade_id) references tb_cidade (id);

alter table tb_restaurante_forma_pagamento add constraint fk_tb_rest_forma_pagto_tb_forma_pagto
foreign key (forma_pagamento_id) references tb_forma_pagamento (id);

alter table tb_restaurante_forma_pagamento add constraint fk_tb_rest_forma_pagto_tb_restaurante
foreign key (restaurante_id) references tb_restaurante (id);

alter table tb_usuario_grupo add constraint fk_tb_usuario_grupo_tb_grupo
foreign key (grupo_id) references tb_grupo (id);

alter table tb_usuario_grupo add constraint fk_tb_usuario_grupo_tb_usuario
foreign key (usuario_id) references tb_usuario (id);

alter table tb_restaurante_usuario_responsavel add constraint fk_restaurante_usuario_restaurante
foreign key (restaurante_id) references tb_restaurante (id);

alter table tb_restaurante_usuario_responsavel add constraint fk_restaurante_usuario_usuario
foreign key (usuario_id) references tb_usuario (id);

alter table tb_pedido
add constraint fk_tb_pedido_tb_endereco_cidade foreign key (endereco_cidade_id) references tb_cidade (id),
add constraint fk_tb_pedido_tb_restaurante foreign key (restaurante_id) references tb_restaurante (id),
add constraint fk_tb_pedido_tb_usuario_cliente foreign key (usuario_cliente_id) references tb_usuario (id),
add constraint fk_tb_pedido_tb_forma_pagamento foreign key (forma_pagamento_id) references tb_forma_pagamento (id);

alter table tb_item_pedido
add constraint fk_tb_item_pedido_tb_pedido foreign key (pedido_id) references tb_pedido (id),
add constraint fk_tb_item_pedido_tb_produto foreign key (produto_id) references tb_produto (id);