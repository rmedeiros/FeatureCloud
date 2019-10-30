package com.onekin.featurecloud.dao;


import com.onekin.featurecloud.model.SnapshotMetada;

import javax.naming.OperationNotSupportedException;

public interface MetadaBoxDAO {
     SnapshotMetada getSnapshotMetadataBox();

     SnapshotMetada getDeltaMetadataBox() throws OperationNotSupportedException;

}
