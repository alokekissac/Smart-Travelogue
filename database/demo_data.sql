-- Demo data for Smart Travelogue (all people and hotels are fictional).
-- Load AFTER schema.sql:  mysql -u root -p smarttravalogue < database/demo_data.sql
-- Logins (username / password):  admin/admin · traveller: meera/demo123
USE `smarttravalogue`;
TRUNCATE `login`; TRUNCATE `users`; TRUNCATE `place_type`; TRUNCATE `places`; TRUNCATE `hotels`;
TRUNCATE `packages`; TRUNCATE `travelogue`; TRUNCATE `uploads`; TRUNCATE `feedback`; TRUNCATE `notifications`;
TRUNCATE `review`; TRUNCATE `interests`; TRUNCATE `history`; TRUNCATE `complaints`;

INSERT INTO `login` VALUES (1,'admin','admin','admin'),(2,'meera','demo123','user'),(3,'daniel','demo123','user'),(4,'priya','demo123','user');

INSERT INTO `users` VALUES
(1,2,'Meera','Nair','Rose Villa','686001','Kottayam','+91 90000 00011','meera@example.com','9.5916','76.5222'),
(2,3,'Daniel','Walsh','12 Harbour Rd','D02','Dublin','+353 80 000 0012','daniel@example.com','53.3498','-6.2603'),
(3,4,'Priya','Menon','Sree Nilayam','682001','Kochi','+91 90000 00013','priya@example.com','9.9312','76.2673');

INSERT INTO `place_type` VALUES (1,'Hill Station'),(2,'Backwaters'),(3,'Beach'),(4,'Heritage'),(5,'Trekking');

INSERT INTO `places` VALUES
(1,'10.0889','77.0595',1,'Munnar','Tea estates, misty peaks and Eravikulam National Park','static/demo/munnar.jpg'),
(2,'9.4981','76.3388',2,'Alleppey','Houseboat cruises through palm-lined canals','static/demo/alleppey.jpg'),
(3,'8.7379','76.7163',3,'Varkala','Red laterite cliffs above the Arabian Sea','static/demo/varkala.jpg'),
(4,'8.8768','76.5926',3,'Kollam Beach','Wide sunset beach by the old port town','static/demo/kollam.jpg'),
(5,'9.9658','76.2421',4,'Fort Kochi','Chinese fishing nets and colonial streets','static/demo/kochi.jpg'),
(6,'11.6854','76.1320',5,'Wayanad','Chembra Peak trek, waterfalls, spice farms','static/demo/wayanad.jpg');

INSERT INTO `hotels` VALUES
(1,'Tea Valley Resort','Hillside rooms overlooking tea estates','10.0880','77.0600','+91 90000 00101','stay@teavalley.example.com','static/demo/hotel_room.jpg','Munnar'),
(2,'Backwater Heritage Inn','Restored villa beside the canals','9.4990','76.3380','+91 90000 00102','hello@bwheritage.example.com','static/demo/hotel_heritage.jpg','Alleppey'),
(3,'Cliffside Boutique','Sea-view rooms on the Varkala cliff','8.7370','76.7160','+91 90000 00103','book@cliffside.example.com','static/demo/hotel_boutique.jpg','Varkala'),
(4,'Harbour Lights Resort','Pool resort near the fishing nets','9.9660','76.2430','+91 90000 00104','info@harbourlights.example.com','static/demo/hotel_resort.jpg','Fort Kochi'),
(5,'Sunset Sands Resort','Beachfront resort with pool','8.8770','76.5930','+91 90000 00105','reserve@sunsetsands.example.com','static/demo/beach_resort.jpg','Kollam');

INSERT INTO `packages` VALUES
(1,1,'Tea Country Weekend','2 nights, breakfast, tea factory tour','8500'),
(2,2,'Houseboat + Heritage Stay','1 night houseboat, 1 night inn','12000'),
(3,3,'Cliff Yoga Retreat','3 nights, daily yoga, breakfast','9600'),
(4,4,'Fort Kochi Culture Break','2 nights, heritage walk, Kathakali show','7200'),
(5,5,'Sunset Beach Escape','2 nights, seafood dinner','6400');

INSERT INTO `travelogue` VALUES
(1,2,'Munnar','Sunrise over the tea hills','Walked Top Station at dawn, then a tea factory tour and fresh cardamom tea.','2026-08-14 07:30'),
(2,3,'Alleppey','A night on the backwaters','Houseboat through the canals, karimeen fry for lunch and a quiet sunset.','2026-08-22 18:10'),
(3,4,'Fort Kochi','Fishing nets and spice markets','Watched the Chinese fishing nets at dusk and got lost in Jew Town.','2026-09-12 17:45'),
(4,2,'Wayanad','Climbing Chembra Peak','Early start, misty trail, and the heart-shaped lake near the top.','2026-09-05 06:20'),
(5,3,'Varkala','Cliffs and cafes','Sunset from the cliff cafes followed by a swim at Papanasam beach.','2026-09-18 18:30');

INSERT INTO `uploads` VALUES
(1,1,2,'static/demo/munnar.jpg','','Tea estates from Top Station','image'),
(2,1,2,'static/demo/trip_forest.jpg','','Forest walk after breakfast','image'),
(3,2,3,'static/demo/alleppey.jpg','','Our houseboat','image'),
(4,2,3,'static/demo/trip_boat.jpg','','Boat ride','image'),
(5,3,4,'static/demo/kochi.jpg','','Harbour at dusk','image'),
(6,4,2,'static/demo/wayanad.jpg','','Chembra ridge','image'),
(7,4,2,'static/demo/trip_trek.jpg','','Nearly at the top','image'),
(8,5,3,'static/demo/varkala.jpg','','Varkala cliff','image'),
(9,5,3,'static/demo/trip_friends.jpg','','With the group','image');

INSERT INTO `feedback` VALUES
(1,2,'Love the travelogue feature','Easy to add photos to each trip.','2026-08-15'),
(2,3,'Hotel suggestions','Would like filters by price.','2026-08-23');

INSERT INTO `notifications` VALUES
(1,'Monsoon travel tips','Pack light rain gear for hill stations.','2026-06-01 09:00'),
(2,'Onam festival week','Special packages available across Kerala.','2026-08-25 10:00');

INSERT INTO `review` VALUES
(1,1,1,'5','Unforgettable sunrise','2026-08-14'),
(2,2,2,'4','Peaceful houseboat night','2026-08-22'),
(3,3,5,'5','So much history','2026-09-12');

INSERT INTO `interests` VALUES (1,1,1),(2,1,6),(3,2,2),(4,3,5);
INSERT INTO `history` VALUES (1,1,1,'2026-08-10'),(2,2,2,'2026-08-20');
INSERT INTO `complaints` VALUES (1,2,'Map pin for Alleppey was slightly off','Thanks! Fixed.','2026-08-24');
