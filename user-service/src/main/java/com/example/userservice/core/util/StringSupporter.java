package com.example.userservice.core.util;


import jakarta.servlet.http.HttpServletRequest;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.SecureRandom;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 문자열 관련 메소드를 제공하는 클래스
 *
 * @since      2017. 10. 19.
 * @version    1.0
 * @author     H2017288
 * <dt>Copyright:</dt>
 * <dd>Copyright (C) by DNP All right reserved.</dd>
 * <dt>Modification Information:</dt>
 * <dd><pre>
 * DATE           AUTHOR          NOTE
 * -------------  --------------  -----------------------
 * 2017. 10. 19.  H2017288        최초 생성
 * </pre></dd>
 */
public class StringSupporter {

	public static final String EMPTY = "";

	private final static String DEFAULT_UNI_CODESET = "8859_1";
	private final static String DEFAULT_LOCAL_CODESET = "KSC5601";
	private static final String lineSeparator = System.getProperty("line.separator");
	private static final String FILE_PATTERN = "^file://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static final String EMAIL_PATTERN = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
	private static final String MOBILE_PATTERN = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";

	/**
	 * 문자열 왼쪽에 특정 갯수만큼 특정 문자열을 붙인다.
	 *
	 * @param sTxt 대상 문자열
	 * @param nNum 최종 문자열 길이
	 * @param chr 붙일 문자열
	 * @return
	 * @Description 한글은 길이 2로
	 */
	public static String lPadByte(String sTxt, int nNum, String chr) {

		if (sTxt == null)
			sTxt = "";

		int len = 0;
		for (int i = 0; i < sTxt.length(); i++) {
			char c = sTxt.charAt(i);

			if (c < 0xac00 || 0xd7a3 < c) {
				len += 1;
			} else {
				len += 2;
			}
		}

		String sParse = "";

		for (int i = 0; i < nNum - len; i++) {
			sParse += chr;
		}

		return sParse + sTxt;
	}

	/**
	 * 문자열 오른쪽에 특정 갯수만큼 특정 문자열을 붙인다.
	 *
	 * @param sTxt 대상 문자열
	 * @param nNum 최종 문자열 길이
	 * @param chr 붙일 문자열
	 * @return
	 * @Description 한글은 길이 2로
	 */
	public static String rPadByte(String sTxt, int nNum, String chr) {

		if (sTxt == null)
			sTxt = "";

		int len = 0;
		for (int i = 0; i < sTxt.length(); i++) {
			char c = sTxt.charAt(i);

			if (c < 0xac00 || 0xd7a3 < c) {
				len += 1;
			} else {
				len += 2;
			}
		}

		String sParse = "";

		for (int i = len; i < nNum; i++) {
			sParse += chr;
		}

		return sTxt + sParse;
	}

	/*
	 * 임시 비밀번호 생성
	 *
	 *  @param size 발생 번호 길이
	 *  @return
	 *  @Description 임시 비밀번호 생성
	 */
	public static String temporaryPassword(int size) {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();

		String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");

		for (int i = 0; i < size; i++) {
			buffer.append(chars[random.nextInt(chars.length)]);
		}

		return buffer.toString();
	}

	/*
	 * 임시 비밀번호 생성
	 *
	 *  @param type 비밀번호 생성 타입
	 *  @param size 발생 번호 길이
	 *  @return
	 *  @Description 임시 비밀번호 생성
	 */
	public static String randomValue(String type, int cnt) {
		StringBuffer strPwd = new StringBuffer();
		char str[] = new char[1];

		// 특수기호 포함
		if (type.equals("P")) {
			for (int i = 0; i < cnt; i++) {
				str[0] = (char) ((Math.random() * 94) + 33);
				strPwd.append(str);
			}

		// 대문자로만
		} else if (type.equals("A")) {
			for (int i = 0; i < cnt; i++) {
				str[0] = (char) ((Math.random() * 26) + 65);
				strPwd.append(str);
			}

		// 소문자로만
		} else if (type.equals("S")) {
			for (int i = 0; i < cnt; i++) {
				str[0] = (char) ((Math.random() * 26) + 97);
				strPwd.append(str);
			}

		// 숫자형으로
		} else if (type.equals("I")) {
			int strs[] = new int[1];
			for (int i = 0; i < cnt; i++) {
				strs[0] = (int) (Math.random() * 9);
				strPwd.append(strs[0]);
			}

		// 소문자, 숫자형
		} else if (type.equals("C")) {
			Random rnd = new Random();
			for (int i = 0; i < cnt; i++) {
				if (rnd.nextBoolean()) {
					strPwd.append((char) ((rnd.nextInt(26)) + 97));
				} else {
					strPwd.append((rnd.nextInt(10)));
				}
			}
		}

		return strPwd.toString();

	}

	/**
	 * 임의 객체를 문자열로 변환한다.
	 *
	 * @param object 변환대상 객체
	 * @return
	 * @Description   문자 null 시 null 반환 조치
	 */
	public static String stringValueOf(Object object) {
		return stringValueOf(object, "");
	}

	/**
	 * 임의 객체를 문자열로 변환한다.
	 *
	 * @param object 변환대상 객체
	 * @param def object가 null일경우 반환할 default string
	 * @return
	 */
	public static String stringValueOf(Object object, String def) {
		return object == null ? def : String.valueOf(object);
	}

	/**
	 * 임의 객체를 문자열로 변환한다.
	 *
	 * @param object 변환대상 객체
	 * @param def object가 null일경우 반환할 default string
	 * @param emptyStringChange 빈값일 경우 문자 변환 여부 default false
	 * @return
	 */
	public static String stringValueOf(Object object, String def, boolean emptyStringChange){
		if(emptyStringChange){
			String returnString = stringValueOf(object, def);
			if(returnString.length() < 1){
				return String.valueOf(def);
			}else{
				return returnString;
			}
		}else{
			return stringValueOf(object, def);
		}
	}

	/*
	 * Clob 를 String 으로 변경
	 */
	public static String clobToString(Clob clob) throws SQLException, IOException {
		BufferedReader br = null;
		StringBuffer strOut = new StringBuffer();
		try{
			if (clob == null) {
				return "";
			}

			String str = "";

			br = new BufferedReader(clob.getCharacterStream());

			while ((str = br.readLine()) != null) {
				strOut.append(str + lineSeparator);
			}

		}catch(SQLException e ){
			throw e;
		}finally{
			if(br != null) br.close();
		}
		return strOut.toString();
	}


	/**
	 * convert local code code set to unicode code set
	 *
	 * @param localStr
	 * @return String
	 * @exception UnsupportedEncodingException
	 */
	public static String toUniCode(String localStr) throws UnsupportedEncodingException {
		if (localStr == null) {
			return null;
		} else {
			return convertCodeSet(localStr, DEFAULT_LOCAL_CODESET, DEFAULT_UNI_CODESET);
		}
	}

	/**
	 * convert unicode code set to local code code set(KSC5601)
	 *
	 * @param uniStr
	 * @return String
	 * @exception UnsupportedEncodingException
	 */
	public static String toLocalCode(String uniStr) throws UnsupportedEncodingException {
		if (uniStr == null) {
			return null;
		} else {
			return convertCodeSet(uniStr, DEFAULT_UNI_CODESET, DEFAULT_LOCAL_CODESET);
		}
	}

	/**
	 * String을 원하는 CodeSet으로 변경
	 * <p>
	 * ex) convertCodeSet("aaaa","8859-1","KSC5601");
	 * </p>
	 *
	 * @param src
	 * @param fromCodeSet
	 * @param toCodeSet
	 * @return String
	 * @exception UnsupportedEncodingException
	 */
	public static String convertCodeSet(String src, String fromCodeSet, String toCodeSet)
			throws UnsupportedEncodingException {

		if (src == null)
			return null;

		return new String(src.getBytes(fromCodeSet), toCodeSet);
	}

	/**
	 * 앞뒤 공백제거
	 * @param str
	 * @return String
	 */
	public static String Trim(String str) {
		if (str == null)
			return "";
		return str.trim();
	}

	/**
	 * 왼쪽 공백제거
	 * @param str
	 * @return String
	 */
	public static String LTrim(String str) {
		if (str == null)
			return "";

		while (str.startsWith(" ")) {
			str = str.substring(1, str.length());
		}
		return str;
	}

	/**
	 * 오른쪽 공백제거
	 * @param str
	 * @return String
	 */
	public static String RTrim(String str) {
		if (str == null)
			return "";

		while (str.endsWith(" ")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * String 앞 또는 뒤를 특정문자로 지정한 길이만큼 채워주는 함수
	 * <p>
	 * (예) pad("1234","0", 6, 1) --> * "123400"
	 * </p>
	 *
	 * @param src : Source string
	 * @param pad : pad string
	 * @param totLen : total length
	 * @param mode   : 앞/뒤 구분 (-1:front, 1:back)
	 * @return String etc : String.format("%02d", String.valueOf(loLastAlloHh)) + String.format("%02d", String.valueOf(loLastAlloMm) );
	 */
	public static String pad(String src, String pad, int totLen, int mode) {
		String paddedString = "";

		if (src == null)
			return "";
		int srcLen = src.length();

		if ((totLen < 1) || (srcLen >= totLen))
			return src;

		for (int i = 0; i < (totLen - srcLen); i++) {
			paddedString += pad;
		}

		if (mode == -1)
			paddedString += src; // front padding
		else
			paddedString = src + paddedString; // back padding

		return paddedString;
	}

	/**
	 * 특정문자를 앞쪽에 padding
	 *
	 * @param str
	 * @param padChar : padded character
	 * @param padLen  : padded length
	 * @return String
	 */
	public static String LPad(String str, char padChar, int padLen) {
		while (str.length() < padLen) {
			str = padChar + str;
		}

		return str;
	}

	/**
	 * 특정문자를 뒤쪽에 padding
	 *
	 * @param str
	 * @param padChar : padded character
	 * @param padLen  : padded length
	 * @return String
	 */
	public static String RPad(String str, char padChar, int padLen) {
		while (str.length() < padLen) {
			str = str + padChar;
		}

		return str;
	}

	/**
	 * For related string, fill the input length from the left with space.<br>
	 *
	 * <pre>
	 * StringUtil.leftPad(null, *) = null
	 * StringUtil.leftPad("", 3) = "   "
	 * StringUtil.leftPad("bat", 3) = "bat"
	 * StringUtil.leftPad("bat", 5) = "  bat"
	 * StringUtil.leftPad("bat", 1) = "bat"
	 * StringUtil.leftPad("bat", -1) = "bat"
	 * </pre>
	 *
	 * @param str
	 *            string to be modified
	 * @param size
	 *            size that includes letter for padding
	 * @return strings for padding <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * For related string, fill the input length from the left with defined
	 * letter.<br>
	 *
	 * <pre>
	 * StringUtil.leftPad(null, *, *) = null
	 * StringUtil.leftPad("", 3, 'z') = "zzz"
	 * StringUtil.leftPad("bat", 3, 'z') = "bat"
	 * StringUtil.leftPad("bat", 5, 'z') = "zzbat"
	 * StringUtil.leftPad("bat", 1, 'z') = "bat"
	 * StringUtil.leftPad("bat", -1, 'z') = "bat"
	 * </pre>
	 *
	 * @param str
	 *            string to be modified
	 * @param size
	 *            size that includes letter for padding
	 * @param padChar
	 *            letter to fill in
	 * @return string that is padded <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size, char padChar) {
		return padString(str, size, String.valueOf(padChar), true);
	}

	/**
	 * For related string, fill the input length from the left with defined string
	 *
	 * <pre>
	 * ex) StringUtil.leftPad(null, *, *) = null
	 *     StringUtil.leftPad("", 3, "z") = "zzz"
	 *     StringUtil.leftPad("bat", 3, "yz") = "bat"
	 *     StringUtil.leftPad("bat", 5, "yz") = "yzbat"
	 *     StringUtil.leftPad("bat", 8, "yz") = "yzyzybat"
	 *     StringUtil.leftPad("bat", 1, "yz") = "bat"
	 *     StringUtil.leftPad("bat", -1, "yz") = "bat"
	 *     StringUtil.leftPad("bat", 5, null) = "  bat"
	 *     StringUtil.leftPad("bat", 5, "") = "  bat"
	 * </pre>
	 *
	 * @param str    : string to be modified
	 * @param size   : size that includes letter for padding
	 * @param padStr : letter to fill in
	 * @return       : string that is padded <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size, String padStr) {
		return padString(str, size, padStr, true);
	}

	private static String padString(String str, int size, String padStr, boolean isLeft)
	{
		if (str == null) {
			return null;
		}
		int originalStrLength = str.length();
		if (size < originalStrLength) {
			return str;
		}
		int difference = size - originalStrLength;

		String tempPad = "";
		if (difference > 0)
		{
			if ((padStr == null) || ("".equals(padStr))) {
				padStr = " ";
			}
			do
			{
				for (int j = 0; j < padStr.length(); j++)
				{
					tempPad = tempPad + padStr.charAt(j);
					if (str.length() + tempPad.length() >= size) {
						break;
					}
				}
			} while (difference > tempPad.length());
			if (isLeft) {
				str = tempPad + str;
			} else {
				str = str + tempPad;
			}
		}
		return str;
	}

	/**
	 * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드
	 *
	 * @param source  : 원본 문자열 배열
	 * @param output  : 더할문자열
	 * @param slength : 지정길이
	 * @return        : 지정길이로 잘라서 더할분자열 합친 문자열
	 */
	public static String cutString(String source, String output, int slength) {
		String returnVal = null;
		if (source != null) {
			if (source.length() > slength) {
				returnVal = source.substring(0, slength) + output;
			} else
				returnVal = source;
		}
		return returnVal;
	}

	/**
	 * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
	 *
	 * @param source  : 원본 문자열 배열
	 * @param slength : 지정길이
	 * @return        : 지정길이로 잘라서 더할분자열 합친 문자열
	 */
	public static String cutString(String source, int slength) {
		String result = null;
		if (source != null) {
			if (source.length() > slength) {
				result = source.substring(0, slength);
			} else
				result = source;
		}
		return result;
	}

	/**
	 * 지정된 길이만큼 문자열 자르기
	 *
	 * @param encoding
	 * @param data
	 * @param maxBytes
	 * @return
	 */
	public static String strCut(String encoding, String data, int maxBytes)
	{
	    if (data == null || data.length() == 0 || maxBytes < 1) {
	        return "";
	    }
	    Charset CS = Charset.forName(encoding);
	    CharBuffer cb = CharBuffer.wrap(data);
	    ByteBuffer bb = ByteBuffer.allocate(maxBytes);
	    CharsetEncoder enc = CS.newEncoder();
	    enc.encode(cb, bb, true);
	    bb.flip();
	    return CS.decode(bb).toString();
	}

	/**
	 * 주어진 수치에 자리수 만큼 "0" 을 붙인 스트링을 얻기위한 Static 메소드
	 *
	 * @param size : 스트링으로 변환 할 전체 자리 수
	 * @param num  : 스트링으로 변환 할 수치(int, long만 가능)
	 * @return String ex) "0001", "0000210"
	 */
	public static String padNumber(int size, long num) {
		String s = "";
		for (int j = size - 1; j >= 0; j--) {
			long l1 = (long) Math.pow(10D, j);
			long l2 = num / l1;
			s = s + l2;
			num -= l2 * l1;
		}
		return s;
	}

	/**
	 * String이 비었거나("") 혹은 null 인지 검증한다.
	 *
	 * <pre>
	 *  ex) npString.isEmpty(null)      = true
	 *      npString.isEmpty("")        = true
	 *      npString.isEmpty(" ")       = false
	 *      npString.isEmpty("bob")     = false
	 *      npString.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str                : 체크 대상 스트링오브젝트이며 null을 허용함
	 * @return <code>true</code> : 입력받은 String 이 빈 문자열 또는 null인 경우
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Determine whether a (trimmed) string is empty
	 *
	 * @param str : The text to check.
	 * @return    : Whether empty.
	 */
	public static boolean isEmptyTrimmed(String str) {
		if(str == null)
			return true;
		return isEmpty(str.trim());
	}

	/**
	 * Check if the entire pattern matches the formal input pattern.
	 *
	 * <pre>
	 * ex) npString.isRegexPatternMatch(&quot;aaaaab&quot;, &quot;a*b&quot;) = true;
	 *     npString.isRegexPatternMatch(&quot;cabbbb&quot;, &quot;a*b&quot;) = false;
	 * </pre>
	 *
	 * @param str     : pattern to be checked
	 * @param pattern : regular expression pattern
	 * @return        : if the input string matches the formal pattern, <code>true</code>
	 */
	public static boolean isRegexPatternMatch(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.
	 *
	 * <pre>
	 * ex) npString.remove(null, *)       = null
	 *     npString.remove("", *)         = ""
	 *     npString.remove("queued", 'u') = "qeed"
	 *     npString.remove("queued", 'z') = "queued"
	 * <pre>
	 *
	 * @param str    : 입력받는 기준 문자열
	 * @param remove : 입력받는 문자열에서 제거할 대상 문자열
	 * @return       : 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	/**
	 * 문자열 내부의 콤마 character(,)를 모두 제거한다
	 *
	 * <pre>
	 * ex) npString.removeCommaChar(null)       = null
	 *     npString.removeCommaChar("")         = ""
	 *     npString.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
	 * </pre>
	 *
	 * @param str : 입력받는 기준 문자열
	 * @return    : " , "가 제거된 입력문자열
	 *              입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String removeCommaChar(String str) {
		return remove(str, ',');
	}

	/**
	 * 문자열 내부의 마이너스 character(-)를 모두 제거한다
	 *
     * <pre>
	 * ex) npString.removeMinusChar(null)       = null
	 *     npString.removeMinusChar("")         = ""
	 *     npString.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
	 * @param str : 입력받는 기준 문자열
	 * @return    : " - "가 제거된 입력문자열
	 *              입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String removeMinusChar(String str) {
		return remove(str, '-');
	}

	/**
	 * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
	 *
	 * @param source  : 원본 문자열
	 * @param subject : 원본 문자열에 포함된 특정 문자열
	 * @param object  : 변환할 문자열
	 * @return        : sb.toString() 새로운 문자열로 변환된 문자열
	 */
	public static String replace(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		String srcStr = source;

		while (srcStr.indexOf(subject) >= 0) {
			preStr = srcStr.substring(0, srcStr.indexOf(subject));
			nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
			srcStr = nextStr;
			rtnStr.append(preStr).append(object);
		}
		rtnStr.append(nextStr);

		return rtnStr.toString();
	}

	/**
	 * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
	 *
	 * @param source  : 원본 문자열
	 * @param subject : 원본 문자열에 포함된 특정 문자열
	 * @param object  : 변환할 문자열
	 * @return        : sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
	 */
	public static String replaceOnce(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		if (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject));
			nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			rtnStr.append(preStr).append(object).append(nextStr);

			return rtnStr.toString();
		} else {
			return source;
		}
	}

	/**
	 * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
	 *
	 * @param source  : 원본 문자열
	 * @param subject : 원본 문자열에 포함된 특정 문자열
	 * @param object  : 변환할 문자열
	 * @return        : sb.toString() 새로운 문자열로 변환된 문자열
	 */
	public static String replaceChar(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		String srcStr = source;

		char chA;

		for (int i = 0; i < subject.length(); i++) {
			chA = subject.charAt(i);

			if (srcStr.indexOf(chA) >= 0) {
				preStr = srcStr.substring(0, srcStr.indexOf(chA));
				nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
				srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
			}
		}

		return srcStr;
	}

	/**
	 * 주어진 sSource스트링 sStart와 sEnd로 기준으로 처음 ~ sStart + replaceStr + sEnd ~ 마지막 , sStart ~ sEnd 형태로 return
	 *
     * <pre>
	 * ex) String[] str = npString.strDivReplace("<BUTTON-WRITE>", "</BUTTON-WRITE>", src, "@@~button-write~@@");
     * </pre>
	 *
	 * @param sSource    : source 스트링
	 * @param sStart     : 첫번째. div
	 * @param sEnd       : 두번째. div
	 * @param replaceStr : 바꿀 String
	 * @return           : String[2]
	 */
	public static String[] strDivReplace(String sStart, String sEnd, String sSource, String replaceStr) {
		String[] rtnBuf = { "", "" };
		String token = "";
		String inStr = sSource;
		int i = 0;

		try {
			token = sStart;
			i = inStr.indexOf(token);
			if (i >= 0) {
				rtnBuf[0] = inStr.substring(0, i);
				inStr = inStr.substring(i + token.length());
				token = sEnd;
				i = inStr.indexOf(token);
				rtnBuf[1] = inStr.substring(0, i);
				sSource = inStr.substring(i + token.length());
				if (!sSource.equals(""))
					rtnBuf[0] += replaceStr + sSource;
				else
					rtnBuf[0] += sSource;
			}
			else {
				rtnBuf[0] = sSource;
				rtnBuf[1] = "";
			}
			return rtnBuf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 주어진 스트링 sStart 와 sEnd로 기준으로 처음 ~ sStart, sStart ~ sEnd, sEnd ~ 마지막 형태로 return
	 *
     * <pre>
	 * ex) String[] str = npString.strDiv("<LOOP>", "</LOOP>", src);
     * </pre>
	 *
	 * @param sSource : source 스트링
	 * @param sStart  : 첫번째. div
	 * @param sEnd    : 두번째. div
	 * @return        : String[3]
	 */
	public static String[] strDiv(String sStart, String sEnd, String sSource) {
		String[] rtnBuf = { "", "", "" };
		String token = "";
		String inStr = sSource;
		int i = 0;
		try {
			token = sStart;
			i = inStr.indexOf(token);

			if (i >= 0) {
				rtnBuf[0] = inStr.substring(0, i);
				inStr = inStr.substring(i + token.length());

				token = sEnd;
				i = inStr.indexOf(token);

				rtnBuf[1] = inStr.substring(0, i);

				rtnBuf[2] = inStr.substring(i + token.length());
			}
			else {
				rtnBuf[0] = sSource;
				rtnBuf[1] = "";
				rtnBuf[2] = "";
			}

			return rtnBuf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 주어진 스트링 hashtable 을 가지고 parsing
	 *
     * <pre>
	 * ex) Strint str = npString.strParsing("@@~", "~@@", src, hash);
     * </pre>
	 *
	 * @param sSource : source 스트링
	 * @param sStart  : 변수시작 String
	 * @param sEnd    : 변수종료 String
	 * @param hVar    : 변수값을 저장한 hashtable
	 * @return        : parsing된 String 값.
	 */
	public static String strParsing(String sStart, String sEnd, String sSource, Hashtable<String,Object> hVar) {
		StringBuffer rtnBuf = new StringBuffer();
		String token = "";
		String inStr = sSource;
		int i = 0;
		String var = "";
		try {
			while (true) {
				token = sStart;
				i = inStr.indexOf(token);

				if (i >= 0) {
					rtnBuf.append(inStr.substring(0, i));
					inStr = inStr.substring(i + token.length());
					token = sEnd;
					i = inStr.indexOf(token);

					var = inStr.substring(0, i);
					var = (String) hVar.get(var);
					if (var == null)
						var = "";
					rtnBuf.append(var);

					inStr = inStr.substring(i + token.length());
				}
				else {
					rtnBuf.append(inStr);
					break;
				}
			}
		} catch (Exception e) {
			rtnBuf = new StringBuffer();
			e.printStackTrace();
		}
		return rtnBuf.toString();
	}

	/**
	 * Formate에 관련된 함수(text가 01252412 이고 format 이 ????-???? 이면 0125-2412로 출력)
	 *
	 * @param text   : 치환할 문자열.
	 * @param format : 치환할 패턴의 형태.
	 * @return       : 치환된 새로운 문자열.
	 */
	public static String getFormatedText(String text, String format) {
		// text가 01252412 이고 format 이 ????-???? 이면 0125-2412로 출력
		String rtn = "";
		int start, i, j, len;
		int tCount, fCount;

		tCount = text.length();
		fCount = format.length();

		if (text.equals(""))
			return rtn;
		// text에서 -를 제거한다.
		for (i = 0; i < tCount; ++i) {
			if (!text.substring(i, i + 1).equals("-"))
				rtn = rtn + text.substring(i, i + 1);
		}

		text = rtn;
		tCount = text.length();

		// 포멧에서 ?의 count
		len = 0;
		for (j = 0; j < fCount; ++j) {
			if (format.substring(j, j + 1).equals("?"))
				++len;
		}
		// text의 길이가 len보다 작으면 앞에 0를 붙인다.
		if (tCount < len) {
			for (i = 0; i < (len - tCount); ++i) {
				text = '0' + text;
			}
			tCount = len;
		}

		rtn = "";
		start = 0;
		for (i = 0; i < tCount; ++i) {
			for (j = start; j < fCount; ++j) {
				if (format.substring(j, j + 1).equals("?")) {
					rtn = rtn + text.substring(i, i + 1);
					start = start + 1;
					break;
				}
				else {
					rtn = rtn + format.substring(j, j + 1);
					start = start + 1;
				}
			}
		}
		return rtn + format.substring(start);
	}

	/**
	 * <code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환한다.
	 * <br>
	 * 입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환
	 *
     * <pre>
	 * ex) npString.indexOf(null, *)          = -1
	 *     npString.indexOf(*, null)          = -1
	 *     npString.indexOf("", "")           = 0
	 *     npString.indexOf("aabaabaa", "a")  = 0
	 *     npString.indexOf("aabaabaa", "b")  = 2
	 *     npString.indexOf("aabaabaa", "ab") = 1
	 *     npString.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
	 * @param str       : 검색 문자열
	 * @param searchStr : 검색 대상문자열
	 * @return          : 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
	 */
	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/**
	 * 오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
	 * <br>
	 * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
	 * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
	 *
     * <pre>
	 * ex) npString.decode(null, null, "foo", "bar")= "foo"
	 *     npString.decode("", null, "foo", "bar") = "bar"
	 *     npString.decode(null, "", "foo", "bar") = "bar"
	 *     npString.decode("하이", "하이", null, "bar") = null
	 *     npString.decode("하이", "하이  ", "foo", null) = null
	 *     npString.decode("하이", "하이", "foo", "bar") = "foo"
	 *     npString.decode("하이", "하이  ", "foo", "bar") = "bar"
     * </pre>
     *
	 * @param sourceStr  :비교할 문자열
	 * @param compareStr : 비교 대상 문자열
	 * @param returnStr  : sourceStr와 compareStr의 값이 같을 때 반환할 문자열
	 * @param defaultStr : sourceStr와 compareStr의 값이 다를 때 반환할 문자열
	 * @return           : sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하고, 다르면 defaultStr을 반환한다.
	 */
	public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
		if (sourceStr == null && compareStr == null) {
			return returnStr;
		}

		if (sourceStr == null && compareStr != null) {
			return defaultStr;
		}

		if (sourceStr.trim().equals(compareStr)) {
			return returnStr;
		}

		return defaultStr;
	}

	/**
	 * 오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
	 * <br>
	 * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
	 * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
	 *
     * <pre>
	 * ex) npString.decode(null, null, "foo") = "foo"
	 *     npString.decode("", null, "foo") = ""
	 *     npString.decode(null, "", "foo") = null
	 *     npString.decode("하이", "하이", "foo") = "foo"
	 *     npString.decode("하이", "하이 ", "foo") = "하이"
	 *     npString.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
	 * @param sourceStr  : 비교할 문자열
	 * @param compareStr : 비교 대상 문자열
	 * @param returnStr  : sourceStr와 compareStr의 값이 같을 때 반환할 문자열
	 * @return           : sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며, 다르면 sourceStr을 반환한다.
	 */
	public static String decode(String sourceStr, String compareStr, String returnStr) {
		return decode(sourceStr, compareStr, returnStr, sourceStr);
	}

	/**
	 * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
	 *
	 * @param object : 원본 객체
	 * @return       : resultVal 문자열
	 */
	public static String isNullToString(Object object) {
		String string = "";

		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}

	/**
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다
	 *
	 * param src : null값일 가능성이 있는 String 값
	 * return    : 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값
	 */
	public static String nullConvert(Object src) {
		//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof BigDecimal) {
			return ((BigDecimal) src).toString();
		}

		if (src == null || src.equals("null")) {
			return "";
		} else {
			return ((String) src).trim();
		}
	}

	/**
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 *
	 * param src : null값일 가능성이 있는 String 값
	 * return    : 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값
	 */
	public static String nullConvert(String src) {

		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return "";
		} else {
			return src.trim();
		}
	}

	/**
	 * 인자로 받은 String이 null일 경우 "0"로 리턴한다.
	 *
	 * param src : null값일 가능성이 있는 String 값
	 * return    : 만약 String이 null 값일 경우 "0"로 바꾼 String 값
	 */
	public static int zeroConvert(Object src) {

		if (src == null || src.equals("null")) {
			return 0;
		} else {
			return Integer.parseInt(((String) src).trim());
		}
	}

	/**
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 *
	 * param src : null값일 가능성이 있는 String 값
	 * return    : 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값
	 */
	public static int zeroConvert(String src) {

		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return 0;
		} else {
			return Integer.parseInt(src.trim());
		}
	}

	/**
	 * 문자열에서 {@link Character#isWhitespace(char)}에 정의된 모든 공백문자를 제거한다.
	 *
	 * <pre>
	 * ex) npString.removeWhitespace(null)         = null
	 *     npString.removeWhitespace("")           = ""
	 *     npString.removeWhitespace("abc")        = "abc"
	 *     npString.removeWhitespace("   ab  c  ") = "abc"
     * </pre>
	 *
	 * @param str  : 공백문자가 제거도어야 할 문자열
	 * @return the : 공백문자가 제거된 문자열, null이 입력되면 <code>null</code>이 리턴
	 */
	public static String removeWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}

		return new String(chs, 0, count);
	}

	/**
	 * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
	 *
	 * @param strString
	 * @return HTML      : 태그를 치환한 문자열
	 */
	public static String checkHtmlView(String strString) {
		String strNew = "";

		StringBuffer strTxt = new StringBuffer("");

		char chrBuff;
		int len = strString.length();

		for (int i = 0; i < len; i++) {
			chrBuff = strString.charAt(i);

			switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '"':
					strTxt.append("&quot;");
					break;
				case 10:
					strTxt.append("<br>");
					break;
				case ' ':
					strTxt.append("&nbsp;");
					break;
				//case '&' :
				//strTxt.append("&amp;");
				//break;
				default:
					strTxt.append(chrBuff);
			}
		}

		strNew = strTxt.toString();

		return strNew;
	}

	/**
	 * 스트링을 HTML형태로 변환시킨다.
	 *
     * <pre>
	 * ex) \n -> &lt;br&gt;, sapce -> &amp;nbsp;, \t -> &amp;nbsp;&amp;nbsp;&amp;nbsp; ..... <br>
	 *     변화시키는 문자들 ( \n, \t, ", < , >, space, &)
     * </pre>
	 *
	 * @param strHtml
	 * @return String
	 */
	public static String convertHTML(String strHtml) {
		if (strHtml == null)
			return "";

		char[] arrStat = strHtml.toCharArray();
		StringBuffer sb = new StringBuffer();
		System.out.println();
		for (int i = 0; i < arrStat.length; i++) {
			System.out.print(arrStat[i]);
			switch (arrStat[i]) {
				case '\n':
					sb.append("<br>");
					break;
				case '\t':
					sb.append("&nbsp;&nbsp;&nbsp;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case ' ':
					sb.append("&nbsp;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				default:
					sb.append(arrStat[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드
	 *
	 * @param source    : 원본 문자열
	 * @param separator : 분리자
	 * @return result   : 분리자로 나뉘어진 문자열 배열
	 */
	public static String[] split(String source, String separator) throws NullPointerException {
		String[] returnVal = null;
		int cnt = 1;

		int index = source.indexOf(separator);
		int index0 = 0;
		while (index >= 0) {
			cnt++;
			index = source.indexOf(separator, index + 1);
		}
		returnVal = new String[cnt];
		cnt = 0;
		index = source.indexOf(separator);
		while (index >= 0) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		returnVal[cnt] = source.substring(index0);

		return returnVal;
	}

	/**
	 * {@link String#toLowerCase()}를 이용하여 소문자로 변환한다.
	 *
     * <pre>
	 * ex) npString.lowerCase(null)  = null
	 *     npString.lowerCase("")    = ""
	 *     npString.lowerCase("aBc") = "abc"
     * </pre>
     *
	 * @param str : 소문자로 변환되어야 할 문자열
	 * @return    : 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * {@link String#toUpperCase()}를 이용하여 대문자로 변환한다.
	 *
     * <pre>
	 * ex) npString.upperCase(null)  = null
	 *     npString.upperCase("")    = ""
	 *     npString.upperCase("aBc") = "ABC"
     * </pre>
     *
	 * @param str : 대문자로 변환되어야 할 문자열
	 * @return    : 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * 입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
	 *
     * <pre>
	 * ex) npString.stripStart(null, *)          = null
	 *     npString.stripStart("", *)            = ""
	 *     npString.stripStart("abc", "")        = "abc"
	 *     npString.stripStart("abc", null)      = "abc"
	 *     npString.stripStart("  abc", null)    = "abc"
	 *     npString.stripStart("abc  ", null)    = "abc  "
	 *     npString.stripStart(" abc ", null)    = "abc "
	 *     npString.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
	 * @param str        : 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars : 제거대상 문자열
	 * @return           : 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}

		return str.substring(start);
	}

	/**
	 * 입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
	 *
     * <pre>
	 * ex) npString.stripEnd(null, *)          = null
	 *     npString.stripEnd("", *)            = ""
	 *     npString.stripEnd("abc", "")        = "abc"
	 *     npString.stripEnd("abc", null)      = "abc"
	 *     npString.stripEnd("  abc", null)    = "  abc"
	 *     npString.stripEnd("abc  ", null)    = "abc"
	 *     npString.stripEnd(" abc ", null)    = " abc"
	 *     npString.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
	 * @param str        : 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars : 제거대상 문자열
	 * @return           : 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}

		return str.substring(0, end);
	}

	/**
	 * 입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.
	 *
     * <pre>
	 * ex) npString.strip(null, *)          = null
	 *     npString.strip("", *)            = ""
	 *     npString.strip("abc", null)      = "abc"
	 *     npString.strip("  abc", null)    = "abc"
	 *     npString.strip("abc  ", null)    = "abc"
	 *     npString.strip(" abc ", null)    = "abc"
	 *     npString.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
	 * @param str        : 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars : 제거대상 문자열
	 * @return           : 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}

		String srcStr = str;
		srcStr = stripStart(srcStr, stripChars);

		return stripEnd(srcStr, stripChars);
	}

	/**
	 * comma가 들어간 문장을 comma를 제거한 String배열로 리턴(tokenizer기능)
	 *
	 * @param str
	 * @return String[]
	 */
	public static String[] getTokenArray(String str) {
		return getTokenArray(str, ",");
	}

	/**
	 * 특정 delimiter로 되있는 String을 delimiter를 제거한 String배열로 리턴(tokenizer기능)
	 *
	 * @param str
	 * @param strDelim
	 * @return String[]
	 */
	public static String[] getTokenArray(String str, String strDelim) {
		if (str == null || str.length() == 0)
			return null;
		StringTokenizer st = new StringTokenizer(str, strDelim);

		String[] arrToken = new String[st.countTokens()];
		for (int i = 0; i < arrToken.length; i++)
			arrToken[i] = st.nextToken();

		return arrToken;
	}

	/**
	 * String을 truncLen길이만큼만 남겨놓고 뒤를 ...으로 붙여줌(가나다라마 -> 가나다...)
	 *
	 * @param str
	 * @param truncLen
	 * @return String
	 */
	public static String truncateString(String str, int truncLen) {
		return truncateString(str, truncLen, "...");
	}

	/**
	 * String을 truncLen길이만큼만 남겨놓고 뒤를 lastStr로 붙여줌
	 *
	 * @param str
	 * @param truncLen
	 * @return String
	 */
	public static String truncateString(String str, int truncLen, String lastStr) {
		if (str == null)
			return str;

		int len = str.length();
		if (len <= truncLen)
			return str;

		return str.substring(0, truncLen) + ((lastStr == null) ? "" : lastStr);
	}

	/**
	 * String을 truncLen길이만큼만 남겨놓고 뒤를 ...으로 붙여줌
	 *
	 * @param str
	 * @param truncLen
	 * @return String
	 */
	public static String truncateCommaString(String str, int truncLen) {

		if (str == null)
			return str;

		// 실제적인 byte 체크
		int strLen = str.length();
		int len = 0;
		char tempChar[] = new char[strLen];
		for (int i = 0; i < strLen; i++) {
			tempChar[i] = str.charAt(i);
			if (tempChar[i] < 128) {
				len += 1;
			}
		}

		int realLen = truncLen + len;
		System.out.println("실제적인 길이 : " + realLen);

		if (realLen < truncLen)
			return str;
		else
			return str.substring(0, realLen) + "...";

	}

	/**
	 * For input strings, remove all strings to be deleted.
	 *
	 * @param str           : input string
	 * @param charsToDelete : string to be deleted
	 * @return String       : deleted string
	 * @see org.springframework.util.npStrings#deleteAny(String, String)
	 */
	public static String deleteAny(String str, String charsToDelete) {
		return org.springframework.util.StringUtils.deleteAny(str,
				charsToDelete);
	}

	/**
	 * Removes all occurrences of given chars from within the source string.
	 *
     * <pre>
	 * ex) char[] ch = new char[2]; ch[0] = 'b'; ch[1] = 'z';
	 *     deleteAny("AbbzzB", ch)) => "AB"
     * </pre>
	 *
	 * @param str           : the source String to search
	 * @param charsToDelete : chars to search for (case insensitive) and remove
	 * @return              : the substring with given chars removed if found
	 */
	public static String deleteAny(String str, char[] charsToDelete) {
		return deleteAny(str, new String(charsToDelete));
	}

	/**
	 * Removes all occurrences of a character from within the source string.
	 *
     * <pre>
	 * ex) deleteAny("ABBBBBC", 'B') => "AC"
     * </pre>
	 *
	 * @param str          : the source String to search
	 * @param charToDelete : the char to search for and remove
	 * @return             : the substring with the char removed if found
	 */
	public static String deleteAny(String str, char charToDelete) {
		return deleteAny(str, String.valueOf(charToDelete));
	}

	/**
	 * 확장자가 있는 화일명을 확장자를 삭제하고 화일명만 넘겨준다.
	 *
	 * @param filename
	 * @return String
	 */
	public static String getFileName(String filename) {
		if (filename == null || filename.length() == 0)
			return filename;

		if (filename.indexOf(".") == -1)
			return filename;

		return filename.substring(0, filename.lastIndexOf("."));
	}

	/**
	 * 확장자가 있는 화일명을 화일명을 삭제하고 확장자만 넘겨준다.
	 *
	 * @param filename
	 * @return String
	 */
	public static String getFileExtension(String filename) {
		if (filename == null)
			return null;

		int len = filename.length();
		if (len == 0)
			return filename;

		int last = filename.lastIndexOf(".");
		if (last == -1)
			return filename;

		return filename.substring(last + 1, len);
	}

	/**
	 * 해당 객체의 메쏘드중 get 메쏘드를 호출하여 메쏘드 이름과 그 값을 line 구분으로 얻는다.
	 *
	 * @param obj
	 * @return get method return value
	 * @exception java.lang.exception
	 *                (java.lang.IllegalAccessException, java.lang.InvocationTargetException)
	 */
	public static String toString(Object obj) throws Exception {
		Class<?> cls = obj.getClass();
		Method[] mt = cls.getMethods();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mt.length; i++) {
			if (mt[i].getName().startsWith("get"))
			{
				sb.append(mt[i].getName());
				sb.append(" : ");
				sb.append(mt[i].invoke(obj, ""));
				if (i < mt.length - 1)
					sb.append("\n");
			}
		}

		return sb.toString();
	}

	/**
	 * 해당 문자열에서 특정 문자를 제거한다.
	 *
     * <pre>
	 * ex) <code>npString.removeChar(&quot;1,234,567,890&quot;, ',');</code> -- &quot;1234567890&quot;
	 *     <code>npString.removeChar(&quot;2001-01-01&quot;, '-');</code> -- &quot;20010101&quot;
	 *     <code>npString.removeChar(&quot;1 2 3 4 5 6 7 8 9 0&quot;, ' ');</code> -- &quot;1234567890&quot;
     * </pre>
	 *
	 * @param str
	 * @param rmChar : 제거할 문자
	 * @return String
	 */
	public static String removeChar(String str, char rmChar) {
		if (str == null || str.indexOf(rmChar) == -1)
			return str;

		StringBuffer sb = new StringBuffer();
		char[] arr = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != rmChar)
				sb.append(arr[i]);
		}

		return sb.toString();
	}

	/**
	 * 해당 문자열에서 특정 문자들을 제거한다.
	 *
     * <pre>
	 * ex) char[] arr = {'-',' ',':'};<br>
	 *     npString.removeChar(&quot;2001-01-01 10:10:10&quot;, arr);
	 *     &quot;20010101101010&quot;
     * </pre>
	 *
	 * @param str
	 * @param rmChar : 제거할 문자
	 * @return String
	 */
	public static String removeChar(String str, char[] rmChar) {
		if (str == null || rmChar == null)
			return str;

		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			boolean bFlag = true;
			for (int k = 0; k < rmChar.length; k++) {
				if (arr[i] == rmChar[k]) {
					bFlag = false;
					break;
				}
			}
			if (bFlag)
				sb.append(arr[i]);
		}

		return sb.toString();
	}

	/**
	 * String을 int값으로 변환한다.
	 *
     * <pre>
	 * ex) int cnt = npString.StrToInt("10");
     * </pre>
	 *
	 * @param str : int값으로 변환될 String문자열
	 * @return    : 변환된 int 값
	 */
	public static int StrToInt(String str) {
		if (str == null)
			return 0;
		return (Integer.valueOf(str).intValue());
	}

	/**
	 * int값을 String으로 변환한다.
	 *
     * <pre>
	 * ex) Strint str = BasenpString.IntToStr(10); <BR>
     * </pre>
	 *
	 * @param i : String으로 변환될 int 값
	 * @return  : 변환된 String 값
	 */
	public static String IntToStr(int i) {
		return (new Integer(i).toString());
	}

	/**
	 * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드
	 *
	 * @param source      : 원본 문자열
	 * @param separator   : 분리자
	 * @param arraylength : 배열 길이
	 * @return            : 분리자로 나뉘어진 문자열 배열
	 */
	public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
		String[] returnVal = new String[arraylength];
		int cnt = 0;
		int index0 = 0;
		int index = source.indexOf(separator);
		while (index >= 0 && cnt < (arraylength - 1)) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		returnVal[cnt] = source.substring(index0);
		if (cnt < (arraylength - 1)) {
			for (int i = cnt + 1; i < arraylength; i++) {
				returnVal[i] = "";
			}
		}

		return returnVal;
	}

	/**
	 * 문자열 A에서 Z사이의 랜덤 문자열을 구하는 기능을 제공 시작문자열과 종료문자열 사이의 랜덤 문자열을 구하는 기능
	 *
	 * @param startChr : 첫 문자
	 * @param endChr   : 마지막문자
	 * @return         : 랜덤문자
	 * @exception      : MyException
	 * @see
	 */
	public static String getRandomStr(char startChr, char endChr) {

		int randomInt;
		String randomStr = null;

		// 시작문자 및 종료문자를 아스키숫자로 변환한다.
		int startInt = Integer.valueOf(startChr);
		int endInt = Integer.valueOf(endChr);

		// 시작문자열이 종료문자열보가 클경우
		if (startInt > endInt) {
			throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
		}

		// 랜덤 객체 생성
		SecureRandom rnd = new SecureRandom();

		do {
			// 시작문자 및 종료문자 중에서 랜덤 숫자를 발생시킨다.
			randomInt = rnd.nextInt(endInt + 1);
		} while (randomInt < startInt); // 입력받은 문자 'A'(65)보다 작으면 다시 랜덤 숫자 발생.

		// 랜덤 숫자를 문자로 변환 후 스트링으로 다시 변환
		randomStr = (char) randomInt + "";

		// 랜덤문자열를 리턴
		return randomStr;
	}

	/**
	 * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을 복원하는 기능을 제공함
	 *
     * <pre>
	 *  String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
	 *  String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서  EUC-KR
     * </pre>
	 *
	 * @param srcString    : 문자열
	 * @param srcCharsetNm : 원래 CharsetNm
	 * @param charsetNm    : CharsetNm
	 * @return             : 인(디)코딩 문자열
	 * @exception MyException
	 * @see
	 */
	public static String getEncdDcd(String srcString, String srcCharsetNm, String cnvrCharsetNm) {

		String rtnStr = null;

		if (srcString == null)
			return null;

		try {
			rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
		} catch (UnsupportedEncodingException e) {
			rtnStr = null;
		}

		return rtnStr;
	}

	/**
     * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다.
     *
     * @param 	srcString '<', '>', '&'가 포함된 문자열
     * @return 	변환문자열
     * @exception MyException
     * @see
     */
	public static String getSpclStrCnvr(String srcString) {

		String rtnStr = null;

		StringBuffer strTxt = new StringBuffer("");

		char chrBuff;
		int len = srcString.length();

		for (int i = 0; i < len; i++) {
			chrBuff = srcString.charAt(i);

			switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '&':
					strTxt.append("&amp;");
					break;
				default:
					strTxt.append(chrBuff);
			}
		}

		rtnStr = strTxt.toString();

		return rtnStr;
	}

	/**
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	 *
	 * @param
	 * @return Timestamp 값
	 * @exception MyException
	 * @see
	 */
	public static String getTimeStamp() {

		String rtnStr = null;

		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		Timestamp ts = new Timestamp(System.currentTimeMillis());

		rtnStr = sdfCurrent.format(ts.getTime());

		return rtnStr;
	}

	/**
	 * Html의 특수문자를 표현하기 위해
	 *
	 * @param srcString
	 * @return String
	 * @exception Exception
	 * @see
	 */
	public static String getHtmlStrCnvr(String srcString) {

		String tmpString = srcString;

		tmpString = tmpString.replaceAll("&lt;", "<");
		tmpString = tmpString.replaceAll("&gt;", ">");
		tmpString = tmpString.replaceAll("&amp;", "&");
		tmpString = tmpString.replaceAll("&nbsp;", " ");
		tmpString = tmpString.replaceAll("&apos;", "\'");
		tmpString = tmpString.replaceAll("&quot;", "\"");

		return tmpString;

	}

	/**
	 * Html의 특수문자를 표현하기 위해
	 * &nbsp; 는 제외
	 *
	 * @param srcString
	 * @return
	 */
	public static String getHtmlStrCnvrExceptNbsp(String srcString) {

		String tmpString = srcString;

		tmpString = tmpString.replaceAll("&lt;", "<");
		tmpString = tmpString.replaceAll("&gt;", ">");
		tmpString = tmpString.replaceAll("&amp;", "&");
		tmpString = tmpString.replaceAll("&apos;", "\'");
		tmpString = tmpString.replaceAll("&quot;", "\"");

		return tmpString;

	}

	/**
	 * 날짜 형식의 문자열 내부에 마이너스 character(-)를 추가한다.
	 *
     * <pre>
	 * ex) npString.addMinusChar("20100901") = "2010-09-01"
     * </pre>
     *
	 * @param date : 입력받는 문자열
	 * @return     : " - "가 추가된 입력문자열
	 */
	public static String addMinusChar(String date) {
		if (date.length() == 8) {
			return date.substring(0, 4).concat("-").concat(date.substring(4, 6)).concat("-").concat(date.substring(6, 8));
		} else {
			return "";
		}
	}

	/**
	 * 스트링으로 넘겨 받은 문자를 123,333형태로 변환한다.
	 *
	 * @param no
	 * @return String
	 */
	public static String getCommaStr(String no) {
		if (no != null && !no.equals("")) {
			int index = no.indexOf(".");
			if (index == -1) {
				return getCommaStr(Long.parseLong(no));
			}
			else {
				return (getCommaStr(Long.parseLong(no.substring(0, index))) + no.substring(index, no.length()));
			}
		}
		else {
			return "";
		}
	}

	/**
	 * 입력 받은 int 값을 문자열로 변환하고, 세자리 마다 콤마를 추가하여 반환한다.
	 *
	 * @param no
	 * @return
	 */
	public static String getCommaStr(int no) {
		return (getCommaStr((new Integer(no)).longValue()));
	}

    /**
     * 입력 받은 long 값을 문자열로 변환하고, 세자리 마다 콤마를 추가하여 반환한다.
     *
     * @param no
     * @return
     */
	public static String getCommaStr(long no) {
		return NumberFormat.getInstance().format(no);
	}

    /**
     * 입력 받은 double 값을 문자열로 변환하고, 세자리 마다 콤마를 추가하여 반환한다.
     *
     * @param no
     * @return
     */
	public static String getCommaStr(double no) {
		return NumberFormat.getInstance().format(no);
	}

    /**
     * 입력 받은 float 값을 문자열로 변환하고, 세자리 마다 콤마를 추가하여 반환한다.
     *
     * @param no
     * @return
     */
	public static String getCommaStr(float no) {
		return (getCommaStr((new Float(no)).doubleValue()));
	}

	/**
	 * IP4 주소를 .을 기준으로 분리한다.
	 *
	 * @param ip : IP4 주소
	 * @return   : 분리된 주소의 배열
	 */
	public static String[] getIpDiv(String ip) {
		if (ip == null || ip.length() == 0)
			return null;
		StringTokenizer st = new StringTokenizer(ip, ".");

		String[] arrToken = new String[st.countTokens()];
		for (int i = 0; i < arrToken.length; i++)
			arrToken[i] = st.nextToken();
		return arrToken;
	}

	/**
	 * IP4 주소를 10진수 형태로 변환한다.
	 *
	 * @param ip : IP4 주소
	 * @return   : IP4의 10진수 주소
	 */
	public static String IpToIntStrValue(String ip) {
		String[] temp = getIpDiv(ip);
		long IntValue = Long.parseLong(temp[0]) * 256 * 256 * 256
				+ Long.parseLong(temp[1]) * 256 * 256
				+ Long.parseLong(temp[2]) * 256
				+ Long.parseLong(temp[3]);
		return String.valueOf(IntValue);
	}

	/**
	 * payload값을  String으로 반환한다.
	 *
	 * @param : request
	 * @return   : payload String 값
	 */
	public static String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}

	/**
	 * String contains 함수와 동일하게 동작하는 String[] 용 대상 문자열 포함여부 함수
	 *
	 * @param arr 문자열 배열
	 * @param target 찾을 문자열
	 * @return
	 */
	public static Boolean contains(String[] arr, String target){
		Boolean flag = false;

		for(String str : arr){
			if(target.equals(str)){
				flag = true;
			}
		}
		return flag;
	}


	/**
	 * 문자열을 확인하여 Unicode를 문자로 변경한다
	 *
	 * @param str 변환 문자열
	 * @return
	 */
	public static String UnicodeConvert(String str) {
		if (str == null) return null;
	    StringBuilder sb = new StringBuilder();
	    char ch;
	    int len = str.length();
	    for (int i = 0; i < len; i++) {
	        ch = str.charAt(i);
	        if (ch == '\\' && str.charAt(i+1) == 'u') {
	            sb.append((char) Integer.parseInt(str.substring(i+2, i+6), 16));
	            i+=5;
	            continue;
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}

	/**
	 * String Array에서 같은 문자열 찾기
	 *
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean hasStr(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
		/*int a = Arrays.binarySearch(arr, targetValue);
		if(a > 0)
			return true;
		else
			return false;*/
	}

	/**
	 * 이메일 인지 검증
	 *
	 * @param email
	 */
	public static boolean isEmail(String email) {
        if (email==null) return false;
        return isRegexPatternMatch(email.trim(), EMAIL_PATTERN);
    }

	/**
	 * 이메일 인지 검증
	 *
	 * @param email
	 */
	public static boolean isMobileNo(String mobile) {
        if (mobile==null) return false;
        return isRegexPatternMatch(mobile.trim(), MOBILE_PATTERN);
    }

	public static boolean isLocalFileName(String path) {
		try {
			if (path==null) return false;
			return isRegexPatternMatch(path.trim(), FILE_PATTERN);
        } catch (RuntimeException e) {
        	return false;
        }
	}

	public static boolean isEnglish(char ch){
        return (ch >= 'A' && ch <= 'Z')
                || (ch >= 'a' && ch <= 'z');
    }

    public static boolean isKorean(char ch) {
        return ch >= Integer.parseInt("AC00", 16)
                && ch <= Integer.parseInt("D7A3", 16);
    }

    public static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isSpecial(char ch) {
        return (ch >= '!' && ch <= '/') // !"#$%&amp;'()*+,-./
                || (ch >= ':' && ch <= '@') //:;&lt;=&gt;?@
                || (ch >= '[' && ch <= '`') //[\]^_`
                || (ch >= '{' && ch <= '~'); //{|}~
    }
}
