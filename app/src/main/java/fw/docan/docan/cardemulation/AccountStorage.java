/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fw.docan.docan.cardemulation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Utility class for persisting account numbers to disk.
 *
 * <p>The default SharedPreferences instance is used as the backing storage. Values are cached
 * in memory for performance.
 *
 * <p>This class is thread-safe.
 */
public class AccountStorage {
    private static final String PREF_ACCOUNT_NUMBER = "account_number";
    private static final String PREF_AMOUNT_NUMBER = "amount_number";

    private static final String PREF_GAS_NUMBER = "gas_number";
    private static final String PREF_GAS_AMOUNT = "gas_amount";

    private static final String DEFAULT_ACCOUNT_NUMBER = "0";
    private static final String TAG = "AccountStorage";
    private static String sAccount = null;
    private static final Object sAccountLock = new Object();
    private static String sAmount = null;
    private static final Object sAmountLock = new Object();


    private static final Object sGasNumberLock = new Object();
    private static String sGasNumber = null;
    private static final Object sGasAmountLock = new Object();
    private static String sGasAmount = null;

    public static void SetAccount(Context c, String s) {
        synchronized(sAccountLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_ACCOUNT_NUMBER, s).commit();
            sAccount = s;
        }
    }

    public static String GetAccount(Context c) {
        synchronized (sAccountLock) {
            if (sAccount == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String account = prefs.getString(PREF_ACCOUNT_NUMBER, DEFAULT_ACCOUNT_NUMBER);
                sAccount = account;
            }
            return sAccount;
        }
    }

    public static void SetAmount(Context c, String s) {
        synchronized(sAmountLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_AMOUNT_NUMBER, s).commit();
            sAmount = s;
        }
    }

    public static String GetAmount(Context c) {
        synchronized (sAmountLock) {
            if (sAmount == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String amount = prefs.getString(PREF_AMOUNT_NUMBER, DEFAULT_ACCOUNT_NUMBER);
                sAmount = amount;
            }
            return sAmount;
        }
    }


    public static void SetGasNumber(Context c, String s) {
        synchronized(sGasNumberLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_GAS_NUMBER, s).commit();
            sGasNumber = s;
        }
    }

    public static String GetGasNumber(Context c) {
        synchronized (sGasNumberLock) {
            if (sGasNumber == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String account = prefs.getString(PREF_GAS_NUMBER, DEFAULT_ACCOUNT_NUMBER);
                sGasNumber = account;
            }
            return sGasNumber;
        }
    }

    public static void SetGasAmount(Context c, String s) {
        synchronized(sGasAmountLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_GAS_AMOUNT, s).commit();
            sGasAmount = s;
        }
    }

    public static String GetGasAmount(Context c) {
        synchronized (sGasAmountLock) {
            if (sGasAmount == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String amount = prefs.getString(PREF_GAS_AMOUNT, DEFAULT_ACCOUNT_NUMBER);
                sGasAmount = amount;
            }
            return sGasAmount;
        }
    }
}
