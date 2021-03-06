SET CURRENT schema RR;
DROP TABLE
    RPT_Mark;
DROP TABLE
    Coupler_Type;
DROP TABLE
    prr_type;
DROP TABLE
    aar_type;
DROP TABLE
    vendor;
DROP TABLE
    manufacturer;
DROP TABLE
    car_type;
DROP TABLE
    kit_type;
DROP TABLE
    car;
DROP TABLE
    inspection;
DROP TABLE
    maintenance;
DROP TABLE
    bad_order;
DROP TABLE
    car_area;
CREATE TABLE
    RPT_Mark
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        Mark         VARCHAR(64) NOT NULL,
        Mark_Desc    VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_Rpt_Mark_Pri
ON
    RPT_Mark
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    RPT_Mark ADD CONSTRAINT RPT_Mark_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE RPT_Mark TO Role Railroad;
CREATE TABLE
    manufacturer
    (
        id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        Manf_name    VARCHAR(64) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_manufacturer_Pri
ON
    manufacturer
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    manufacturer ADD CONSTRAINT manufacturer_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE manufacturer TO Role Railroad;
CREATE TABLE
    vendor
    (
        id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        Source_Name  VARCHAR(64) NOT NULL,
        description  VARCHAR(255),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_vendor_Pri
ON
    vendor
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    vendor ADD CONSTRAINT vendor_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE vendor TO Role Railroad;
CREATE TABLE
    Coupler_Type
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_Coupler_Pri
ON
    Coupler_Type
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    Coupler_Type ADD CONSTRAINT Coupler_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Coupler_Type TO Role Railroad;
CREATE TABLE
    PRR_Type
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_PRR_Type_Pri
ON
    PRR_Type
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    PRR_Type ADD CONSTRAINT PRR_Type_Pri_key PRIMARY KEY ( ID );
CREATE UNIQUE INDEX
    IDX_PRR_TYPE_IX1
ON
    PRR_type
    (
        TYPE
    );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE PRR_Type TO Role Railroad;
CREATE TABLE
    AAR_Type
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_AAR_Type_Pri
ON
    AAR_Type
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    AAR_Type ADD CONSTRAINT AAR_Type_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE AAR_Type TO Role Railroad;
CREATE TABLE
    Car_Type
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_CAR_Type_Pri
ON
    CAR_Type
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    CAR_Type ADD CONSTRAINT CAR_Type_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE CAR_Type TO Role Railroad;
CREATE TABLE
    KIT_Type
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_KIT_Type_Pri
ON
    Kit_Type
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    KIT_Type ADD CONSTRAINT KIT_Type_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE KIT_Type TO Role Railroad;
CREATE TABLE
    Car
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        RPT_Mark       INT NOT NULL,
        PRR_Type       INT,
        cplr_type      INT,
        kit_type       INT,
        car_type       INT,
        aar_type       INT,
        SOURCE         INT,
        manufacturer   INT,
        car_number     VARCHAR(64) NOT NULL,
        purchased      DATE,
        kit_built      DATE,
        in_service     DATE,
        purchase_price DECIMAL(7,2),
        LENGTH         DECIMAL(9,0),
        weight         DECIMAL(5,2),
        BLT_Date       CHAR(5),
        resist_Wheels  char(1) not null default '0',
        weathered      char(1) not null default '0',
        car_Color      VARCHAR(48),
        description    VARCHAR(2000),
        rfid_tag       VARCHAR(18),
        wheels         VARCHAR(64),
        last_updated   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT con_prr_type FOREIGN KEY ( prr_type ) REFERENCES prr_type ( id ),
        CONSTRAINT con_cplr_type FOREIGN KEY (cplr_type ) REFERENCES coupler_type ( id ),
        CONSTRAINT con_kit_type FOREIGN KEY ( kit_type ) REFERENCES kit_type ( id ),
        CONSTRAINT con_vendor FOREIGN KEY ( SOURCE ) REFERENCES vendor ( id ),
        CONSTRAINT con_manf FOREIGN KEY ( manufacturer ) REFERENCES manufacturer ( id ),
        CONSTRAINT number_unique UNIQUE (rpt_mark, car_number),
        CONSTRAINT rpt_mark_key FOREIGN KEY ( rpt_mark ) REFERENCES rpt_mark ( id ),
        CONSTRAINT aar_type_key FOREIGN KEY (aar_type ) REFERENCES aar_type (id),
        CONSTRAINT car_type_key FOREIGN KEY (car_type ) REFERENCES car_type (id)
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_Car_Pri
ON
    Car
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
CREATE INDEX
    IDX_Car_RFID
ON
    Car
    (
        RFID_Tag
    )
    ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    Car ADD CONSTRAINT Car_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Car TO Role Railroad;
CREATE TABLE
    Inspection
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        car_ID              INT NOT NULL,
        inspect_Date        DATE NOT NULL,
        carWeight           DECIMAL(5,1),
        carLength           DECIMAL(5,1),
        inspectionTime      DECIMAL(7,1),
        OVERALL_passed      CHAR(1) NOT NULL DEFAULT '0',
        weightPassed        CHAR(1) NOT NULL DEFAULT '0',
        CPLR_A_HEIGHT       CHAR(1) NOT NULL DEFAULT '0',
        CPLR_B_HEIGHT       CHAR(1) NOT NULL DEFAULT '0',
        CPLR_A_ACTION       CHAR(1) NOT NULL DEFAULT '0',
        CPLR_B_ACTION       CHAR(1) NOT NULL DEFAULT '0',
        METAL_WHEELS_A      CHAR(1) NOT NULL DEFAULT '0',
        METAL_WHEELS_B      CHAR(1) NOT NULL DEFAULT '0',
        RESIST_WHEELS_A     CHAR(1) NOT NULL DEFAULT '0',
        RESIST_WHEELS_B     CHAR(1) NOT NULL DEFAULT '0',
        WHEEL_GAUGE_A       CHAR(1) NOT NULL DEFAULT '0',
        WHEEL_GAUGE_B       CHAR(1) NOT NULL DEFAULT '0',
        WHEEL_TREAD_A       CHAR(1) NOT NULL DEFAULT '0',
        WHEEL_TREAD_B       CHAR(1) NOT NULL DEFAULT '0',
        TRUCK_SCREW_LOOSE_A CHAR(1) NOT NULL DEFAULT '0',
        TRUCK_SCREW_TIGHT_B CHAR(1) NOT NULL DEFAULT '0',
        CAR_SITS_LEVEL      CHAR(1) NOT NULL DEFAULT '0',
        CAR_DOESNT_ROCK     CHAR(1) NOT NULL DEFAULT '0',
        ALL_WHEELS_TOUCH    CHAR(1) NOT NULL DEFAULT '0',
        last_updated        TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
        CONSTRAINT inspect_parent_key FOREIGN KEY (Car_ID) REFERENCES car(ID)
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_Inspection_Pri
ON
    Inspection
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    Inspection ADD CONSTRAINT Inspection_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Inspection TO Role Railroad;

CREATE TABLE
    Car_area
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        TYPE         VARCHAR(64) NOT NULL,
        Description  VARCHAR(255),
        LAST_Updated TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_car_area_Pri
ON
    Car_area
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    car_area ADD CONSTRAINT car_area_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Car_area TO Role Railroad;
CREATE TABLE
    Maintenance
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        Maint_Date        DATE NOT NULL,
        Car_ID            INT NOT NULL,
        area_of_car       INT,
        closed_Bad_orders CHAR(1) NOT NULL DEFAULT '0',
        problem_desc      VARCHAR(1000),
        work_performed    VARCHAR(1000),
        last_updated      TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
        CONSTRAINT maint_parent_key FOREIGN KEY (car_Id) REFERENCES car ( id ),
        CONSTRAINT car_area_key FOREIGN KEY (area_of_car) REFERENCES car_area ( id )
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_Maintenance_Pri
ON
    Maintenance
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    Maintenance ADD CONSTRAINT Maintenance_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Maintenance TO Role Railroad;
CREATE TABLE
    Bad_Order
    (
        ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 INCREMENT BY 1
        MINVALUE -2147483648 MAXVALUE 2147483647 NO CYCLE CACHE 20 NO ORDER ),
        Car_ID         INT NOT NULL,
        area_of_car    INT NOT NULL,
        closed_by      INT,
        in_effect      CHAR(1) NOT NULL DEFAULT '1',
        out_of_service CHAR(1) NOT NULL DEFAULT '1',
        date_entered   DATE NOT NULL,
        date_closed    DATE,
        description    VARCHAR(2000),
        last_updated   TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
        CONSTRAINT Bad_order_parent_key FOREIGN KEY (car_Id) REFERENCES car ( id ),
        CONSTRAINT Bad_order_area FOREIGN KEY (area_of_car) REFERENCES car_area ( id ),
        CONSTRAINT Closed_by_key FOREIGN KEY (closed_by) REFERENCES maintenance ( id )
    )
    DATA CAPTURE NONE INDEX IN RRSPACE LONG IN RRSPACE COMPRESS YES STATIC VALUE COMPRESSION;
CREATE UNIQUE INDEX
    IDX_bad_order_Pri
ON
    Bad_order
    (
        ID
    )
    CLUSTER ALLOW Reverse Scans Compress Yes;
ALTER TABLE
    Bad_Order ADD CONSTRAINT Bad_order_Pri_key PRIMARY KEY ( ID );
GRANT
INSERT
    ,
UPDATE
    ,
SELECT
    ,
DELETE
ON
    TABLE Bad_Order TO Role Railroad;
INSERT INTO
    coupler_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'Kadee',
        'Standard Kadee #5'
    );
INSERT INTO
    coupler_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'Sargent',
        'Sargent Prototypical'
    );
INSERT INTO
    kit_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'rtr',
        'Ready to Run'
    );
INSERT INTO
    kit_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'brass',
        'High Quality Brass'
    );
INSERT INTO
    kit_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'shake',
        'Shake the Box'
    );
INSERT INTO
    kit_type
    (
        TYPE,
        description
    )
    VALUES
    (
        'craftsman',
        'Craftsman'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'gondola',
        'Gondola'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'box',
        'Box'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'flat',
        'Flatcar'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'reefer',
        'Refrigerator car'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'covered hopper',
        'Covered Hopper car'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'tank',
        'Tank car'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'caboose',
        'Cabin car'
    );
INSERT INTO
    RR.CAR_TYPE
    (
        TYPE,
        DESCRIPTION
    )
    VALUES
    (
        'hopper',
        'Hopper car'
    );

INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'coupler',
        'Coupler'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'wheels',
        'Car Wheels'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'trucks',
        'Trucks'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'underframe',
        'Car underframe'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'body',
        'Body of car'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'safety appliances',
        'Safety Appliances (Steps, ladders, etc.)'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'rfidtag',
        'RFID Tag'
    );
INSERT INTO
    car_area
    (
        TYPE,
        description
    )
    VALUES
    (
        'Other',
        'other than a standard area'
    );

INSERT INTO
    vendor
    (
        source_name,
        description
    )
    VALUES
    (
        'Other',
        'Other than a vendor listed here'
    );

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Other',
        'Other than a manufacturer listed here'
    );

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Kadee',
        'Kadee'
    );

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Quality Craft',
        'Quality Craft'
    );    

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Ambroid',
        'One of 5000 Kit'
    );    

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Westerfield',
        'Manufacturer of resin car kits'
    );    

INSERT INTO
    manufacturer
    (
        manf_name,
        description
    )
    VALUES
    (
        'Funero & Cumerlingo',
        'Manufacturer of resin car kits'
    );    





