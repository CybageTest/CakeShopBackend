package com.littlejoys.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class OtpService {
	public static final Integer EXPIRE_MINS=5;
	private LoadingCache otpCache;
	private static final Random RANDOM= new Random();
	
	public OtpService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader() {
			public Integer load(String key) {
				return 0;
			}

			@Override
			public Object load(Object key) throws Exception {
				// TODO Auto-generated method stub
				return 0;
			}
		});	
	}
		
	public int generateotp(String key) {
		int otp = 100000 + RANDOM.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	public int getOtp(String key) {
		try {
			return (int) otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	public void clearOtp(String key) {
		otpCache.invalidate(key);
	}

	
}
