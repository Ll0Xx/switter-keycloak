db.createUser({user: "user", pwd: "pass", roles: [{role: "readWrite", db: "switter-users-db"}]});

conn = new Mongo();
db = conn.getDB("switter-users-db");

db.role.createIndex({"name": 1}, {unique: true});

db.role.insert({"name": "USER"});
db.role.insert({"name": "ADMIN"});