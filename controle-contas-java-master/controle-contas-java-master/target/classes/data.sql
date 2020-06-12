insert into cliente(id, nome, tipocliente, cpfcnpj, nomefantasia) values (900, 'Dante', 'PJ', '80.435.325/0001-36', 'Dante Fantasia')
insert into cliente(id, nome, tipocliente, cpfcnpj, nomefantasia) values (901, 'Uriel', 'PJ', '81.971.893/0001-14', 'Uriel Fantasia')
insert into cliente(id, nome, tipocliente, cpfcnpj, nomefantasia) values (902, 'Marcos', 'PJ', '92.486.032/0001-67', 'Marcos Fantasia')

insert into conta(id, nome, numero, saldo, tipoconta, situacao, datacriacao, contapai, cliente) values (800, 'Banco do HUE', '123456-8', 50.0, 'MATRIZ', 'ATIVA', SYSDATE, null, 900)
insert into conta(id, nome, numero, saldo, tipoconta, situacao, datacriacao, contapai, cliente) values (801, 'Itaivis', '4654-8', 70.0, 'FILIAL', 'ATIVA', SYSDATE, 800, 901)
insert into conta(id, nome, numero, saldo, tipoconta, situacao, datacriacao, contapai, cliente) values (802, 'Bradivis', '121228', 120.0, 'FILIAL', 'ATIVA', SYSDATE, 800, 902)