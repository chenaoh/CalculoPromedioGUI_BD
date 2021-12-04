CREATE DATABASE `bd_notas` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE 'estudiante' (
  'documento_est' varchar(10) NOT NULL,
  'nombre_est' varchar(45) DEFAULT NULL,
  'nota1' decimal(3,2) DEFAULT NULL,
  'nota2' decimal(3,2) DEFAULT NULL,
  'nota3' decimal(3,2) DEFAULT NULL,
  'promedio' decimal(3,2) DEFAULT NULL,
  PRIMARY KEY ('documento_est')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;