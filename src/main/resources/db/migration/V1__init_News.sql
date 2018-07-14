CREATE TABLE news (
  id bigint(11) NOT NULL AUTO_INCREMENT,
  city_id bigint(11) NOT NULL DEFAULT '0',
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  author bigint(11) NOT NULL,
  title varchar(10000) NOT NULL,
  content LONGTEXT NOT NULL,
  deleted int(11) NOT NULL,
  for_teacher bigint(11) NOT NULL,
  photo varchar(1000) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8