package dev.ohhoonim.lms.domain.utils;

public record Condition<T, I>(T condition, Page<I> page) {

    public Condition {
        if (page == null) {
            page = new Page<I>(1, 10);
        }
    }
    /**
     * 페이지 정보를 담고 있는 클래스
     * @param page 현재 페이지
     * @param size 페이지당 표시할 데이터 수
     * @param lastSeenKey 페이지당 표시할 데이터에서 마지막으로 조회된 key
     */
    public record Page<I>(int page, int size, I lastSeenKey) {
        public Page(int page, int size) {
            this(page, size, null);
        }

        /**
         * 페이지당 표시할 데이터의 시작 인덱스를 반환한다. 데이터베이스별 페이징 쿼리는 다음과 같다
         * DB별 사용법이 귀찮다면 "FETCH FIRST {size} ROWS ONLY"를 사용하자
         * offset 보다는 lastSeenKey 사용하는 것을 권장한다. 대용량 데이터에서는 속도차가 크다.
         * 
         * - MySQL: "limit {offset}, {size}"를 사용하지 말고,
         * <pre>
            SELECT * FROM students 
            WHERE student_id > {lastSeenKey}
            ORDER BY height DESC,name ASC 
            LIMIT {size}
         * </pre>
         * 
         * - Oracle: 정렬이 먼저 수행되므로 subquery로 처리한다
         * <pre>
           SELECT * FROM (
                SELECT a.*, ROWNUM as rnum FROM (
                    SELECT * FROM students ORDER BY height DESC,name ASC
                    )
                a)  WHERE rnum >= {offset} and rnum <= {offset} + {size}
         * </pre>
         * "FETCH FIRST {size} ROWS ONLY"를 사용해도 된다. (PostgreSQL 예제 참고)
         * 
         * - PostgreSQL: "offset {offset} limit {size}"를 사용하지 말고,
        * <pre>
           SELECT * FROM students 
           WHERE student_id > {lastSeenKey}
           ORDER BY height DESC,name ASC 
           LIMIT {size}
         * </pre>
         * "LIMIT {size}" 대신에 "FETCH FIRST {size} ROWS ONLY"를 써도 plan까지 똑같다.
        **/
        public int offset() {
            return (page - 1) * size;
        }
    }
}