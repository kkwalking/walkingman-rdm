package com.kelton.walkingmanrdm.common;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * @Author kelton
 * @Date 2024/4/20 16:52
 * @Version 1.0
 */
public class Constants {

    public static final String APPLICATION_NAME = "walkingman-rdm";

    public static final String HOME_DATA_PATH = FileUtil.getUserHomePath() + File.separator + APPLICATION_NAME;


    /** code log ********** begin **/
    public static final String HOME_CODE_LOG_FILE_PATH = HOME_DATA_PATH + File.separator + "logs" + File.separator + APPLICATION_NAME + ".log";
    public static final String RELATIVE_CODE_LOG_FILE_PATH = "."        + File.separator + "logs" + File.separator + APPLICATION_NAME + ".log";
    /** code log ********** end **/


    public static final String HOME_DERBY_PATH = HOME_DATA_PATH + File.separator + "derby";
    public static final String RELATIVE_DERBY_PATH = "." + File.separator + "derby";

    /** derby log ********** begin **/
    public static final String HOME_DERBY_LOG_FILE = HOME_DERBY_PATH + File.separator + "derby.log";
    public static final String RELATIVE_DERBY_LOG_FILE = RELATIVE_DERBY_PATH + File.separator + "derby.log";
    /** derby log ********** end **/

    /** derby data ********** begin **/
    public static final String HOME_DERBY_DATA_FILE = HOME_DERBY_PATH + File.separator + "data";
    public static final String RELATIVE_DERBY_DATA_FILE = RELATIVE_DERBY_PATH + File.separator + "data";
    /** derby data ********** end **/

    public static final String PROPERTY_FILE = HOME_DATA_PATH + File.separator + "setting.properties";

    public static final String DATA_INIT = "dataInit";
}
