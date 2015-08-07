psql -U postgres -c "CREATE USER @@@APP@@@ WITH PASSWORD '@@@APP@@@';"
psql -U postgres -c "DROP DATABASE IF EXISTS @@@APP@@@;"
psql -U postgres -c "CREATE DATABASE @@@APP@@@ OWNER @@@APP@@@ TEMPLATE template_postgis ENCODING 'UTF8';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE @@@APP@@@ TO @@@APP@@@;"