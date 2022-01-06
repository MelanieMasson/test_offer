--
-- Base de données : test_atos
--

-- --------------------------------------------------------

--
-- Structure de la table user
--

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
    id int(11) NOT NULL AUTO_INCREMENT,
    username varchar(100) NOT NULL,
    birthday date NOT NULL,
    country varchar(50) NOT NULL,
    phone varchar(20) DEFAULT NULL,
    gender enum('Male', 'Female', 'Other') DEFAULT NULL,
    PRIMARY KEY (id)
    ) ;

--
-- Déchargement des données de la table user
--

INSERT INTO user (id, username, birthday, country, phone, gender) VALUES
    (1, 'melanie.masson', '1998-12-19', 'France', '0123456789', 'Female'),
    (2, 'pierre.dupont', '1983-07-27', 'France', '0123456789', 'Male'),
    (3, 'jean.martin', '1973-10-05', 'France', '0123456789', 'Other');

//COMMIT;