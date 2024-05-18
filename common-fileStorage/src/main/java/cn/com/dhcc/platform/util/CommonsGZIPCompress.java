/**
 *  Copyright (c)  2018-2028 DHCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of DHCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with DHCC.
 */
package cn.com.dhcc.platform.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;
import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;



/**
 * 
 * @author Jerry.chen
 * @date 2019年2月26日
 */
@Slf4j
public class CommonsGZIPCompress {

	/**
	 * Google Snappy 压缩算法
	 * 
	 * @param srcBytes
	 * @return byte[]
	 * @throws IOException
	 * @author Jerry.chen
	 * @date 2019年3月8日
	 */
	public static byte[] compress(byte srcBytes[]) throws IOException {
//        return  Snappy.compress(srcBytes);
		LZ4BlockOutputStream compressedOutput = null;
		ByteArrayOutputStream byteOutput;
		try {
			LZ4Factory factory = LZ4Factory.fastestInstance();
			byteOutput = new ByteArrayOutputStream();
			LZ4Compressor compressor = factory.fastCompressor();
			compressedOutput = new LZ4BlockOutputStream(byteOutput, 2048, compressor);
			compressedOutput.write(srcBytes);
		} finally {
			compressedOutput.close();
		}
		return byteOutput.toByteArray();
	}

	/**
	 * Google Snappy 解压缩算法
	 * 
	 * @param bytes
	 * @return byte[]
	 * @throws IOException
	 * @author Jerry.chen
	 * @date 2019年3月8日
	 */
	public static byte[] uncompress(byte[] bytes) throws IOException {
//		return Snappy.uncompress(bytes);
		ByteArrayOutputStream baos;
		LZ4BlockInputStream lzis = null;
		try {
			LZ4Factory factory = LZ4Factory.fastestInstance();
			baos = new ByteArrayOutputStream();
			LZ4FastDecompressor decompresser = factory.fastDecompressor();
			lzis = new LZ4BlockInputStream(new ByteArrayInputStream(bytes), decompresser);
			int count;
			byte[] buffer = new byte[2048];
			while ((count = lzis.read(buffer)) != -1) {
				baos.write(buffer, 0, count);
			}

		} finally {
			lzis.close();
		}
		return baos.toByteArray();
	}

	/***
	 * 压缩GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] gZip(byte[] data) {
//		byte[] b = null;
//		ByteArrayOutputStream bos = null;
//		GZIPOutputStream gzip = null;
//		try {
//			bos = new ByteArrayOutputStream();
//			gzip = new GZIPOutputStream(bos);
//			gzip.write(data);
//			gzip.finish();
//			b = bos.toByteArray();
//		} catch (Exception ex) {
//			log.error("压缩GZip--gZip出现异常！", ex);
//		} finally {
//			try {
//				if (null != bos) {
//					bos.close();
//				}
//				if (null != gzip) {
//					gzip.close();
//				}
//			} catch (IOException e) {
//				log.error("关闭流出现异常", e);
//			}
//		}
//		return b;
		try {
			return compress(data);
		} catch (IOException e) {
			log.error("压缩GZip--gZip出现异常！", e);
		}
		return null;
	}

	/***
	 * 解压GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unGZip(byte[] data) throws Exception {
//		byte[] b = null;
//		ByteArrayInputStream bis = null;
//		GZIPInputStream gzip = null;
//		ByteArrayOutputStream baos = null;
//		try {
//			bis = new ByteArrayInputStream(data);
//			gzip = new GZIPInputStream(bis);
//			byte[] buf = new byte[1024];
//			int num = -1;
//			baos = new ByteArrayOutputStream();
//			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
//				baos.write(buf, 0, num);
//			}
//			b = baos.toByteArray();
//			baos.flush();
//		} catch (Exception ex) {
//			log.error("解压GZip出现异常", ex);
//		} finally {
//			if (null != bis) {
//				bis.close();
//			}
//			if (null != baos) {
//				baos.close();
//			}
//			if (null != gzip) {
//				gzip.close();
//			}
//		}
//		return b;
		return uncompress(data);
	}
	public static byte[] unGZipV2(byte[] data) throws Exception {
		byte[] b = null;
		ByteArrayInputStream bis = null;
		GZIPInputStream gzip = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new ByteArrayInputStream(data);
			gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
		} catch (Exception ex) {
			log.error("解压GZip出现异常", ex);
		} finally {
			if (null != bis) {
				bis.close();
			}
			if (null != baos) {
				baos.close();
			}
			if (null != gzip) {
				gzip.close();
			}
		}
		return b;
//		return uncompress(data);
	}
	
	/***
	 * 解压中心GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unGZipPboc(byte[] data) throws Exception {
		byte[] b = null;
		ByteArrayInputStream bis = null;
		GZIPInputStream gzip = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new ByteArrayInputStream(data);
			gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
		} catch (Exception ex) {
			log.error("解压中心GZip出现异常", ex);
		} finally {
			if (null != bis) {
				bis.close();
			}
			if (null != baos) {
				baos.close();
			}
			if (null != gzip) {
				gzip.close();
			}
		}
		return b;
	}

	/***
	 * 压缩Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] zip(byte[] data) {
		byte[] b = null;
		ByteArrayOutputStream bos = null;
		ZipOutputStream zip = null;
		try {
			bos = new ByteArrayOutputStream();
			zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			b = bos.toByteArray();
		} catch (Exception ex) {
			log.error("压缩Zip出现异常！", ex);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (zip != null) {
					zip.close();
				}
			} catch (IOException e) {
				log.error("关闭输入输出流异常", e);
			}

		}
		return b;
	}

	/***
	 * 解压Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unZip(byte[] data) {
		byte[] b = null;
		ByteArrayInputStream bis = null;
		ZipInputStream zip = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new ByteArrayInputStream(data);
			zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
			}
		} catch (Exception ex) {
			log.error("解压Zip出现异常", ex);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (zip != null) {
					zip.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				log.error("关闭IO流出现异常", e);
			}

		}
		return b;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 
	 * 使用gzip进行压缩
	 */

	@SuppressWarnings({ "restriction" })
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			log.error("使用gzip进行压缩出现异常", e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					log.error("关闭IO流出现异常", e);
				}
			}
		}
//		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
		return  Base64.getEncoder().encodeToString(out.toByteArray());
	}

	@SuppressWarnings({ "restriction" })
	public static byte[] gzipStrToBytes(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			log.error("关闭IO流出现异常", e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					log.error("关闭IO流出现异常", e);
				}
			}
		}
		byte[] res = out.toByteArray();
		return res;
	}

	/**
	 *
	 * <p>
	 * Description:使用gzip进行解压缩
	 * </p>
	 * 
	 * @param compressedStr
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String gunzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		String decompressed = null;
		try {
			in = new ByteArrayInputStream(compressedStr.getBytes());
			ginzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			log.error("IO流操作出现异常", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (ginzip != null) {
					ginzip.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error("关闭IO流出现异常", e);
			}
		}
		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * 
	 * @param str 压缩前的文本
	 * @return 返回压缩后的文本
	 */
	@SuppressWarnings("restriction")
	public static final String zip(String str) {
		if (str == null) {
			return null;
		}
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
//			compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
			compressedStr = Base64.getEncoder().encodeToString(compressed);
		} catch (IOException e) {
			compressed = null;
			log.error("使用zip进行压缩出现异常", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (zout != null) {
					zout.close();
				}
			} catch (IOException e) {
				log.error("关闭IO流出现异常", e);
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed 压缩后的文本
	 * @return 解压后的字符串
	 */
	@SuppressWarnings("restriction")
	public static final String unzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
//			byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			byte[] compressed = Base64.getDecoder().decode(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
			log.error("使用zip进行解压缩出现异常", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (zin != null) {
					zin.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error("关闭IO流出现异常", e);
			}
		}
		return decompressed;
	}
}
