INSERT INTO gameMode(id, slug) VALUES
    (1, 'single-player'),
    (2, 'multiplayer'),
    (3, 'co-operative'),
    (4, 'split-screen'),
    (5, 'massively-multiplayer-online-mmo'),
    (6, 'battle-royale')
    ;
INSERT INTO gameModeName(id, gameModeId, languageCode, name) VALUES
    (1,1,'eng','Single player'),
    (2,2,'eng','Multiplayer'),
    (3,3,'eng','Co-operative'),
    (4,4,'eng','Split screen'),
    (5,5,'eng','Massively Multiplayer Online (MMO)'),
    (6,6,'eng','Battle Royale')
    ;
INSERT INTO platform(id, slug) VALUES
    (6, 'win'),
    (14, 'mac'),
    (3, 'linux'),
    (48, 'ps4--1'),
    (167, 'ps5'),
    (46, 'psvita'),
    (49, 'xboxone'),
    (169, 'series-x'),
    (130, 'switch'),
    (37, '3ds'),
    (39, 'ios'),
    (34, 'android')
    ;

INSERT INTO platformName(id, gamePlatformId, languageCode, name) VALUES
    (1, 6, 'eng', 'PC (Microsoft Windows)'),
    (2, 14, 'eng', 'Mac OS'),
    (3, 3, 'eng', 'Linux'),
    (4, 48, 'eng', 'PlayStation 4'),
    (5, 167, 'eng', 'PlayStation 5'),
    (6, 46, 'eng', 'PS Vita'),
    (7, 49, 'eng', 'Xbox One'),
    (8, 169, 'eng', 'Xbox Series X|S'),
    (9, 130, 'eng', 'Nintendo Switch'),
    (10, 37, 'eng', 'Nintendo 3DS'),
    (11, 39, 'eng', 'iOS'),
    (12, 34, 'eng', 'Android')
    ;

INSERT INTO playerPerspective(id, slug) VALUES
    (1, 'first-person'),
    (2, 'third-person'),
    (3, 'bird-view-slash-isometric'),
    (4, 'side-view'),
    (5, 'text'),
    (6, 'auditory'),
    (7, 'virtual-reality')
    ;
INSERT INTO playerPerspectiveName(id, playerPerspectiveId, languageCode, name) VALUES
    (1, 1, 'eng', 'First person'),
    (2, 2, 'eng', 'Third person'),
    (3, 3, 'eng', 'Bird view / Isometric'),
    (4, 4, 'eng', 'Side view'),
    (5, 5, 'eng', 'Text'),
    (6, 6, 'eng', 'Auditory'),
    (7, 7, 'eng', 'Virtual Reality')
    ;

VACUUM;
