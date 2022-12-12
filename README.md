SCHEMA
CREATE TABLE rsvp (
	id int auto_increment not null,
    name varchar(64) not null,
    email varchar(64),
    phone varchar(64),
    confirmation_date datetime,
    comments longtext,
    primary key(id)
);

POSTMAN
For POST and PUT mapping:
Under -> Body -> Raw:
{
    "name": "",
    "email": "",
    "phone": "",
    "confirmation_date": "2022-01-30",
    "comments": ""
}