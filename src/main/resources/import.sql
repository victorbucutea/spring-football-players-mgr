
insert into Team (id,name) values(1,'Barcelona');
insert into Team (id,name) values(2,'Real Madrid');
insert into Team (id,name) values(3,'Manchester');

insert into Player (id, team_id, name, num, position,image) values(1,1,'Lionel Messi', 10, 'Forward','messi.jpg');
insert into Player (id, team_id, name, num, position,image) values(2,1,'Andreas Inniesta', 8, 'Midfielder','innesta.jpg');
insert into Player (id, team_id, name, num, position,image) values(3,1,'Pique Gerard', 3, 'Defender','gerard.jpg');


insert into User (id, username, role, password, enabled ) values(1,'Pierluigi_Colina','match_referee', 'secret', true);
insert into User (id, username, role, password,enabled) values(2,'Nicola_Rizzoli', 'referee','secret',true);