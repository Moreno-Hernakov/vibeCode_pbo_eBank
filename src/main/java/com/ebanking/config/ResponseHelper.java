package com.ebanking.config;

/**
 * Helper sakti buat urusan Response Code (RC)
 * Biar kaga capek ngetik if-else mulu tiap dapet balikan dari DB
 */
public class ResponseHelper {
    
    public static final String SUCCESS = "00";

    /**
     * Cek apakah RC sukses atau kaga
     * @param rc Response Code dari database/SP
     * @return true kalo "00", false kalo yang lain (atau null)
     */
    public static boolean isSuccess(String rc) {
        return SUCCESS.equals(rc);
    }
}
