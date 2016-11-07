package nus.iss.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nus.iss.model.Delivery;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-07T17:53:50")
@StaticMetamodel(Pod.class)
public class Pod_ { 

    public static volatile SingularAttribute<Pod, String> note;
    public static volatile SingularAttribute<Pod, byte[]> image;
    public static volatile SingularAttribute<Pod, String> ackId;
    public static volatile SingularAttribute<Pod, Delivery> pkgId;
    public static volatile SingularAttribute<Pod, Date> deliveryDate;
    public static volatile SingularAttribute<Pod, Integer> podId;

}