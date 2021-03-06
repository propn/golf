package com.golf.mvc;

import com.golf.utils.StringUtils;

public abstract class Pattern {

    private static final AllMatch ALL_MATCH = new AllMatch();

    /**
     * 根据模式匹配字符串
     * 
     * @param str 进行匹配的字符串
     * @return 返回null表示匹配失败，否则返回匹配的字符串数组
     */
    abstract public String[] match(String str);

    public static Pattern compile(String pattern, String wildcard) {
        final boolean startWith = pattern.startsWith(wildcard);
        final boolean endWith = pattern.endsWith(wildcard);
        final String[] array = StringUtils.split(pattern, wildcard);

        switch (array.length) {
        case 0:
            return ALL_MATCH; // pattern全是通配符
        case 1:
            if (startWith && endWith)
                return new HeadAndTailMatch(array[0]);

            if (startWith)
                return new HeadMatch(array[0]);

            if (endWith)
                return new TailMatch(array[0]);

            return new EqualsMatch(pattern); // pattern不包含通配符
        default:
            return new MultipartMatch(startWith, endWith, array);
        }
    }

    private static class MultipartMatch extends Pattern {

        private final boolean startWith, endWith;
        private final String[] parts;
        private int num;

        public MultipartMatch(boolean startWith, boolean endWith, String[] parts) {
            super();
            this.startWith = startWith;
            this.endWith = endWith;
            this.parts = parts;
            num = parts.length - 1;
            if (startWith)
                num++;
            if (endWith)
                num++;
        }

        @Override
        public String[] match(String str) {
            int currentIndex = -1;
            int lastIndex = -1;
            String[] ret = new String[num];

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                int j = startWith ? i : i - 1;
                currentIndex = str.indexOf(part, lastIndex + 1);

                if (currentIndex > lastIndex) {
                    if (i != 0 || startWith)
                        ret[j] = str.substring(lastIndex + 1, currentIndex);

                    lastIndex = currentIndex + part.length() - 1;
                    continue;
                }
                return null;
            }

            if (endWith)
                ret[num - 1] = str.substring(lastIndex + 1);

            return ret;
        }

    }

    private static class TailMatch extends Pattern {
        private final String part;

        public TailMatch(String part) {
            this.part = part;
        }

        @Override
        public String[] match(String str) {
            int currentIndex = str.indexOf(part);
            if (currentIndex == 0) {
                return new String[] { str.substring(part.length()) };
            }
            return null;
        }
    }

    private static class HeadMatch extends Pattern {
        private final String part;

        public HeadMatch(String part) {
            this.part = part;
        }

        @Override
        public String[] match(String str) {
            int currentIndex = str.indexOf(part);
            if (currentIndex + part.length() == str.length()) {
                return new String[] { str.substring(0, currentIndex) };
            }
            return null;
        }

    }

    private static class HeadAndTailMatch extends Pattern {
        private final String part;

        public HeadAndTailMatch(String part) {
            this.part = part;
        }

        @Override
        public String[] match(String str) {
            int currentIndex = str.indexOf(part);
            if (currentIndex >= 0) {
                String[] ret = new String[] { str.substring(0, currentIndex),
                        str.substring(currentIndex + part.length(), str.length()) };
                return ret;
            }
            return null;
        }

    }

    private static class EqualsMatch extends Pattern {
        private final String pattern;

        public EqualsMatch(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public String[] match(String str) {
            return pattern.equals(str) ? new String[0] : null;
        }
    }

    private static class AllMatch extends Pattern {

        @Override
        public String[] match(String str) {
            return new String[] { str };
        }
    }
}
