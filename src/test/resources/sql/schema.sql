CREATE TABLE  java_locale (
  id bigint NOT NULL,
  language varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  variant varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE language_code_baidu (
  id bigint NOT NULL,
  code varchar(45) NOT NULL,
  display_name varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE java_locale_baidu_mapping (
  java_locale_id bigint NOT NULL,
  baidu_code_id bigint DEFAULT 0,
  PRIMARY KEY (java_locale_id,baidu_code_id)
);

CREATE VIEW 
	v_java_locale_baidu_mapping 
AS 
	select 
		j.id as locale_id,
		j.language as language,
		j.country as country,
		j.variant as variant,
		j.display_name as locale_name,
		b.id as baidu_id,
		b.code as baidu_code,
		b.display_name as baidu_name 
	from java_locale as j 
		left join java_locale_baidu_mapping as m 
			on j.id=m.java_locale_id 
		left join language_code_baidu as b 
			on m.baidu_code_id=b.id;

