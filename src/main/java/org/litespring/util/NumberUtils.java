package org.litespring.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public abstract class NumberUtils {

    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {

        Assert.notNull(text, "Text must not be null");
        Assert.notNull(targetClass, "target class must not be null");

        String trimmed = StringUtils.trimAllWhitespace(text);

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        } else if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        } else if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        } else if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        } else if (targetClass.equals(BigDecimal.class)||targetClass.equals(Number.class)) {
            return (T) new  BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException("Can not convert String [" + text +
                    "] to target class [" + targetClass.getName()+
                    "]");
        }

    }

    public static <T extends Number> T parseNumber(String text, Class<T> targetClass, NumberFormat numberFormat) {
        if (numberFormat != null) {
            Assert.notNull(text, "Text must not be null");
            Assert.notNull(targetClass, "target class must not be null");
            DecimalFormat decimalFormat = null;
            boolean resetBigDecimal = false;
            if (numberFormat instanceof DecimalFormat) {
                decimalFormat = (DecimalFormat) numberFormat;
                if (BigDecimal.class.equals(targetClass) && !decimalFormat.isParseBigDecimal()) {
                    decimalFormat.setParseBigDecimal(true);
                    resetBigDecimal = true;
                }
            }
            try {
                Number number = numberFormat.parse(StringUtils.trimAllWhitespace(text));
                return convertNumberToTargetClass(number, targetClass);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("could not parse number:" + ex.getMessage());
            }finally {
                if (resetBigDecimal) {
                    decimalFormat.setParseBigDecimal(false);
                }
            }
        } else {
           return  parseNumber(text, targetClass);
        }
    }

    public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass) throws IllegalArgumentException{
        Assert.notNull(number,"number must not be a null");
        Assert.notNull(targetClass, "target class must not be null");

        if (targetClass.isInstance(number)) {
            return (T) number;

        } else if (targetClass.equals(Byte.class)) {
            long value = number.longValue();
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Byte(number.byteValue());
        } else if (targetClass.equals(Short.class)) {
            long value = number.longValue();
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Short(number.shortValue());
        } else if (targetClass.equals(Integer.class)) {
            long value = number.longValue();
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Integer(number.intValue());
        } else if (targetClass.equals(Long.class)) {
            return (T) new Long(number.longValue());
        } else if (targetClass.equals(BigInteger.class)) {
            if (number instanceof BigDecimal) {
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (targetClass.equals(Float.class)) {
            return (T) new Float(number.floatValue());
        } else if (targetClass.equals(Double.class)) {
            return (T) new Double(number.doubleValue());
        } else if (targetClass.equals(BigDecimal.class)) {
            return (T) new BigDecimal(number.toString());
        } else {
            throw new IllegalArgumentException("Could not convert number[" +
                    number + "] of type [" + number.getClass().getName() + "] to unknow target class [" + targetClass.getName() + " ]");
        }

    }

    private static void raiseOverflowException(Number number, Class targetClass) {

        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
                number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }

    private static BigInteger decodeBigInteger(String value) {
        int radix = 0;
        int index = 0;
        boolean negative = false;

        if (value.startsWith("-")) {
            negative = true;
            index++;

        }
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;

        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;

        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }



}
