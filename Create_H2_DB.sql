CREATE schema
IF NOT EXISTS RR;
SET schema RR;
DROP TABLE 
    IF EXISTS aar_type;
DROP TABLE 
    IF EXISTS car_type;
DROP TABLE 
    IF EXISTS coupler_type;
DROP TABLE 
    IF EXISTS kit_type;
DROP TABLE 
    IF EXISTS prr_type;
DROP TABLE 
    IF EXISTS rpt_mark;
DROP TABLE 
    IF EXISTS car;
DROP TABLE 
    IF EXISTS maintenance;
DROP TABLE 
    IF EXISTS inspection;
DROP TABLE 
    IF EXISTS bad_order;
    Drop table IF EXISTS car_area;
CREATE TABLE
    RPT_MARK
    (
        id identity PRIMARY KEY,
        mark         VARCHAR(64) UNIQUE NOT NULL,
        mark_desc    VARCHAR,
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE UNIQUE INDEX
    Rpt_mark_idx
ON
    rpt_mark
    (
        mark
    );
CREATE TABLE
    Coupler_Type
    (
        id identity PRIMARY KEY,
        type         VARCHAR(64) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE UNIQUE INDEX
    Coupler_type_idx
ON
    Coupler_Type
    (
        type
    );
CREATE TABLE
    PRR_type
    (
        id identity PRIMARY KEY,
        type         VARCHAR(64) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE UNIQUE INDEX
    PRR_type_idx
ON
    PRR_type
    (
        type
    );
CREATE TABLE
    aar_type
    (
        id identity PRIMARY KEY,
        type         VARCHAR(18) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE TABLE
    car_type
    (
        id identity PRIMARY KEY,
        type         VARCHAR(18) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE TABLE
    kit_type
    (
        id identity PRIMARY KEY,
        type         VARCHAR(64),
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    );
CREATE UNIQUE INDEX
    type_idx
ON
    kit_type
    (
        type
    );
    CREATE TABLE 
    Car_area
    (
        ID IDENTITY PRIMARY KEY
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    );
CREATE TABLE
    car
    (
        id identity PRIMARY KEY,
        prr_type     INT,
        cplr_type    INT,
        kit_type     INT,
        car_type     INT,
        car_number   VARCHAR(64) NOT NULL,
        purchased    DATE,
        kit_built    DATE,
        in_service   DATE,
        LENGTH       DECIMAL(9,0),
        weight       DECIMAL(9,0),
        rpt_mark     INT NOT NULL,
        aar_type     INT,
        BLT_Date     CHAR(5),
        car_Color    VARCHAR(48),
        description  VARCHAR(2000),
        rfid_tag     VARCHAR(18),
        wheels       CHAR(64),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT con_prr_type FOREIGN KEY ( prr_type ) REFERENCES prr_type ( id ),
        CONSTRAINT con_cplr_type FOREIGN KEY (cplr_type ) REFERENCES coupler_type ( id ),
        CONSTRAINT con_kit_type FOREIGN KEY ( kit_type ) REFERENCES kit_type ( id ),
        CONSTRAINT number_unique UNIQUE (rpt_mark, car_number),
        CONSTRAINT rpt_mark_key FOREIGN KEY ( rpt_mark ) REFERENCES rpt_mark ( id ),
        CONSTRAINT aar_type_key FOREIGN KEY (aar_type ) REFERENCES aar_type (id),
        CONSTRAINT car_type_key FOREIGN KEY (car_type ) REFERENCES car_type (id)
    );
CREATE INDEX
    rfid_idx
ON
    car
    (
        rfid_tag
    );
CREATE TABLE
    inspection
    (
        id identity PRIMARY KEY,
        car_id             INT NOT NULL,
        inspect_date       DATE NOT NULL,
        passed             CHAR(1) NOT NULL DEFAULT '0',
        weightPassed       CHAR(1) NOT NULL DEFAULT '0',
        cplr_height_Passed CHAR(1) NOT NULL DEFAULT '0',
        gauge_passed       CHAR(1) NOT NULL DEFAULT '0',
        last_updated       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT inspect_parent_key FOREIGN KEY ( car_id ) REFERENCES car ( id )
    );
CREATE TABLE
    maintenance
    (
        id identity PRIMARY KEY,
        maint_date       DATE,
        car_id           INT NOT NULL,
        complete         CHAR(1) NOT NULL DEFAULT '0',
        maintenance_desc CHAR(1000),
        last_updated     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT maint_parent_key FOREIGN KEY (car_id) REFERENCES car ( id )
    );
CREATE TABLE
    bad_order
    (
        id identity PRIMARY KEY,
        car_id       INT NOT NULL,
        in_effect    BOOLEAN DEFAULT true NOT NULL,
        description  VARCHAR(2000),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT bo_parent_key FOREIGN KEY (car_id) REFERENCES car ( id )
    );       