package com.peterkohli.scoretracker;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/12/15
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DatabaseObject {
    public void write(Connection conn)
            throws SQLException;

    public void delete(Connection conn)
            throws SQLException;
}
