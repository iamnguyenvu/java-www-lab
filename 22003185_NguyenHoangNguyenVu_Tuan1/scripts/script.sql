CREATE table contacts (
    contact_id int not null auto_increment,
    first_name varchar(45) default null,
    last_name varchar(45) default null,
    photo BLOB,
    primary key (contact_id)
)

ALTER TABLE contacts MODIFY photo LONGBLOB;
