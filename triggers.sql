DROP TRIGGER IF EXISTS `db_test`.`package_validityperiod_purchases_AFTER_INSERT`;
DROP TRIGGER IF EXISTS `db_test`.`package_purchases_AFTER_INSERT`;
DROP TRIGGER IF EXISTS `db_test`.`op_purchases_AFTER_INSERT`;
DROP TRIGGER IF EXISTS `db_test`.`op_purchases_AFTER_UPDATE`;

DELIMITER $$
USE `db_test`$$

CREATE TRIGGER `package_validityperiod_purchases_AFTER_INSERT` 
AFTER INSERT ON `service_activation_schedule`
FOR EACH ROW
BEGIN
	DECLARE spid INT;
    DECLARE vpid INT;
    SELECT o.sp_id INTO spid FROM `order` AS o WHERE o.id = new.order_id;
	SELECT o.vp_id INTO vpid FROM `order` AS o WHERE o.id = new.order_id;
    IF NOT exists (SELECT* FROM `package_validityperiod_purchases` WHERE `sp_id` = spid AND `vp_id` = vpid) THEN
		INSERT INTO `package_validityperiod_purchases` (sp_id, vp_id, purchases) VALUES (spid,vpid,1);
	ELSE
		UPDATE `package_validityperiod_purchases` SET `purchases` = `purchases`+ 1
				WHERE `sp_id` = spid AND `vp_id`=vpid;
	END IF;
END$$

CREATE TRIGGER `package_purchases_AFTER_INSERT`
AFTER INSERT ON `service_activation_schedule`
FOR EACH ROW
BEGIN
	DECLARE op_fees INT;
    DECLARE vp_month INT;
    DECLARE count_op INT;
    DECLARE old_purchases INT;
    DECLARE spid INT;
    DECLARE total INT;
    
    SELECT o.sp_id INTO spid FROM `order` AS o WHERE new.order_id = o.id;
    SELECT o.total INTO total FROM `order` AS o WHERE new.order_id = o.id;
    
	SELECT coalesce(sum(op.fee), 0) INTO op_fees FROM `optional_product` AS op, `order_optprod` AS oop
		WHERE new.order_id=oop.order_id AND oop.optprod_id=op.id;
	SELECT vp.months INTO vp_month FROM `validity_period` AS vp, `order` AS o
		WHERE o.vp_id=vp.id AND new.order_id=o.id;
	SELECT coalesce(count(*), 0) INTO count_op FROM `order_optprod` AS oop, `order`  AS o
		WHERE o.sp_id = spid AND o.id = oop.order_id AND oop.order_status=1;
	SELECT pp.total_purchases INTO old_purchases FROM `package_purchases` AS pp, `order` AS o
		WHERE o.sp_id=pp.sp_id AND new.order_id=o.id;
	
	IF NOT EXISTS (SELECT * FROM `package_purchases` AS pp WHERE pp.sp_id=spid) THEN
		INSERT INTO `package_purchases` (sp_id, total_purchases, total_sales_op, total_sales_not_op, average_op) 
			VALUES (spid, 1, total, total-(op_fees*vp_month), count_op);
	ELSE
		UPDATE `package_purchases`
		SET `total_purchases`=old_purchases + 1, `total_sales_op`= `total_sales_op` + total,
			`total_sales_not_op`= `total_sales_not_op` + (total - (op_fees*vp_month)),
			`average_op`=count_op/(old_purchases + 1)
		WHERE `sp_id`=spid;
	END IF;

END$$

CREATE TRIGGER `op_purchases_AFTER_INSERT` 
AFTER INSERT ON `order_optprod`
FOR EACH ROW
BEGIN
    DECLARE op_fee INT;
    DECLARE vp_month INT;
	IF (new.order_status = 1) THEN
		SELECT op.fee INTO op_fee FROM `optional_product` AS op
			WHERE new.optprod_id=op.id;
		SELECT vp.months INTO vp_month FROM `validity_period` AS vp, `order` AS o
			WHERE o.vp_id=vp.id AND o.id=new.order_id;
		IF NOT EXISTS (SELECT * FROM `op_purchases` AS opp WHERE opp.op_id=new.optprod_id) THEN
			INSERT INTO `op_purchases` (op_id, op_sales) VALUES (new.optprod_id, op_fee*vp_month);
		ELSE 
			UPDATE `op_purchases` 
            SET `op_sales`= `op_sales` + (op_fee*vp_month)
            WHERE `op_id`=new.optprod_id;
		END IF;
    END IF;
END$$

CREATE TRIGGER `op_purchases_AFTER_UPDATE` 
AFTER UPDATE ON `order_optprod`
FOR EACH ROW
BEGIN
    DECLARE op_fee INT;
    DECLARE vp_month INT;
    IF (new.order_status = 1) THEN
		SELECT op.fee INTO op_fee FROM `optional_product` AS op
			WHERE new.optprod_id=op.id;
		SELECT vp.months INTO vp_month FROM `validity_period` AS vp, `order` AS o
			WHERE o.vp_id=vp.id AND o.id=new.order_id;
		IF NOT EXISTS (SELECT * FROM `op_purchases` AS opp WHERE opp.op_id=new.optprod_id) THEN
			INSERT INTO `op_purchases` (op_id, op_sales) VALUES (new.optprod_id, op_fee*vp_month);
		ELSE 
			UPDATE `op_purchases` 
            SET `op_sales`= `op_sales` + (op_fee*vp_month)
            WHERE `op_id`=new.optprod_id;
		END IF;
    END IF;
END$$



DELIMITER ;