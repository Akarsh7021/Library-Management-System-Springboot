--
-- PostgreSQL database dump
--

\
restrict F3yGhoBQhoBomshnajNToT2kNffum7LY1xbasIqbl4vcxaTq0qze3mn5AX7tJ7W

-- Dumped from database version 15.14 (Debian 15.14-0+deb12u1)
-- Dumped by pg_dump version 18.0

-- Started on 2025-11-30 15:19:38

SET statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
transaction_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

DROP
DATABASE info2413;
--
-- TOC entry 3476 (class 1262 OID 16389)
-- Name: info2413; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE
DATABASE info2413 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_CA.UTF-8';


ALTER
DATABASE info2413 OWNER TO postgres;

\unrestrict
F3yGhoBQhoBomshnajNToT2kNffum7LY1xbasIqbl4vcxaTq0qze3mn5AX7tJ7W
\connect info2413
\restrict F3yGhoBQhoBomshnajNToT2kNffum7LY1xbasIqbl4vcxaTq0qze3mn5AX7tJ7W

SET statement_timeout = 0;
SET
lock_timeout = 0;
SET
idle_in_transaction_session_timeout = 0;
SET
transaction_timeout = 0;
SET
client_encoding = 'UTF8';
SET
standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET
check_function_bodies = false;
SET
xmloption = content;
SET
client_min_messages = warning;
SET
row_security = off;

--
-- TOC entry 16 (class 2615 OID 19398)
-- Name: schema_name; Type: SCHEMA; Schema: -; Owner: info2413
--

CREATE SCHEMA schema_name;


ALTER
SCHEMA schema_name OWNER TO info2413;

--
-- TOC entry 881 (class 1247 OID 17075)
-- Name: full_name; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.full_name AS (
    first_name smallint,
    last_name smallint,
    middle_names smallint
    );


ALTER TYPE public.full_name OWNER TO postgres;

SET
default_tablespace = '';

SET
default_table_access_method = heap;

--
-- TOC entry 236 (class 1259 OID 19237)
-- Name: Account; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Account"
(
    account_id         integer NOT NULL,
    account_type       character varying(255),
    full_name          character varying(255),
    notification_email character varying(255),
    password_hash      character varying(255),
    phone_number       character varying(255)
);


ALTER TABLE public."Account" OWNER TO info2413;

--
-- TOC entry 237 (class 1259 OID 19244)
-- Name: AccountPublication; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."AccountPublication"
(
    account_id_account  integer NOT NULL,
    isbn_13_publication bigint  NOT NULL,
    waitlist_position   smallint
);


ALTER TABLE public."AccountPublication" OWNER TO info2413;

--
-- TOC entry 226 (class 1259 OID 19227)
-- Name: Account_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Account_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Account_SEQ" OWNER TO info2413;

--
-- TOC entry 250 (class 1259 OID 19359)
-- Name: Account_account_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Account" ALTER COLUMN account_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Account_account_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 238 (class 1259 OID 19249)
-- Name: Author; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Author"
(
    author_id   integer NOT NULL,
    author_name character varying(255)
);


ALTER TABLE public."Author" OWNER TO info2413;

--
-- TOC entry 227 (class 1259 OID 19228)
-- Name: Author_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Author_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Author_SEQ" OWNER TO info2413;

--
-- TOC entry 257 (class 1259 OID 19407)
-- Name: Author_author_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Author" ALTER COLUMN author_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Author_author_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 241 (class 1259 OID 19264)
-- Name: Borrow; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Borrow"
(
    "account_id_Account"      integer,
    borrow_id                 integer NOT NULL,
    borrowed_date             date,
    due_date                  date,
    returned_date             date,
    "serial_barcode_BookCopy" integer,
    status                    character varying(255)
);


ALTER TABLE public."Borrow" OWNER TO info2413;

--
-- TOC entry 243 (class 1259 OID 19274)
-- Name: Fine; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Fine"
(
    "borrow_id_Borrow" integer,
    fine_amount        integer,
    fine_id            integer NOT NULL,
    issue_date         date,
    waived_reversed    boolean
);


ALTER TABLE public."Fine" OWNER TO info2413;

--
-- TOC entry 246 (class 1259 OID 19289)
-- Name: Payment; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Payment"
(
    "account_id_Account" integer,
    payment_date         date,
    payment_id           integer                  NOT NULL,
    payment_method       character varying(255),
    payment_amount       numeric(10, 2) DEFAULT 0 NOT NULL,
    processed_by         integer
);


ALTER TABLE public."Payment" OWNER TO info2413;

--
-- TOC entry 254 (class 1259 OID 19379)
-- Name: Balance; Type: VIEW; Schema: public; Owner: info2413
--

CREATE VIEW public."Balance" AS
SELECT a.account_id,
       a.full_name,
       COALESCE(sum(f.fine_amount), (0)::bigint) AS total_fines,
       COALESCE(sum(p.total_paid), (0)::numeric) AS total_payments,
       ((COALESCE(sum(f.fine_amount), (0)::bigint))::numeric - COALESCE(sum(p.total_paid), (0)::numeric)) AS current_balance
   FROM (((public."Account" a
     LEFT JOIN public."Borrow" b ON ((b."account_id_Account" = a.account_id)))
     LEFT JOIN public."Fine" f ON ((f."borrow_id_Borrow" = b.borrow_id)))
     LEFT JOIN ( SELECT "Payment"."account_id_Account",
            sum("Payment".payment_amount) AS total_paid
           FROM public."Payment"
          GROUP BY "Payment"."account_id_Account") p ON ((p."account_id_Account" = a.account_id)))
  GROUP BY a.account_id, a.full_name
  ORDER BY a.account_id;


ALTER
VIEW public."Balance" OWNER TO info2413;

--
-- TOC entry 239 (class 1259 OID 19254)
-- Name: BookCopy; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."BookCopy"
(
    date_acquired         date,
    "isbn_13_Publication" bigint  NOT NULL,
    serial_barcode        integer NOT NULL
);


ALTER TABLE public."BookCopy" OWNER TO info2413;

--
-- TOC entry 240 (class 1259 OID 19259)
-- Name: BookCopyConditionNotes; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."BookCopyConditionNotes"
(
    "serial_barcode_BookCopy" integer                NOT NULL,
    condition_notes           character varying(255) NOT NULL,
    note_id                   integer                NOT NULL,
    created_at                timestamp with time zone
);


ALTER TABLE public."BookCopyConditionNotes" OWNER TO info2413;

--
-- TOC entry 228 (class 1259 OID 19229)
-- Name: BookCopyConditionNotes_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."BookCopyConditionNotes_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."BookCopyConditionNotes_SEQ" OWNER TO info2413;

--
-- TOC entry 256 (class 1259 OID 19391)
-- Name: BookCopyConditionNotes_note_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."BookCopyConditionNotes" ALTER COLUMN note_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."BookCopyConditionNotes_note_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 229 (class 1259 OID 19230)
-- Name: BookCopy_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."BookCopy_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."BookCopy_SEQ" OWNER TO info2413;

--
-- TOC entry 242 (class 1259 OID 19269)
-- Name: BorrowRenewalDate; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."BorrowRenewalDate"
(
    "borrow_id_Borrow" integer,
    renewal_date       date NOT NULL
);


ALTER TABLE public."BorrowRenewalDate" OWNER TO info2413;

--
-- TOC entry 230 (class 1259 OID 19231)
-- Name: Borrow_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Borrow_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Borrow_SEQ" OWNER TO info2413;

--
-- TOC entry 251 (class 1259 OID 19360)
-- Name: Borrow_borrow_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Borrow" ALTER COLUMN borrow_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Borrow_borrow_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 258 (class 1259 OID 19547)
-- Name: Configuration; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Configuration"
(
    config_key   character varying(255) NOT NULL,
    config_value character varying(255)
);


ALTER TABLE public."Configuration" OWNER TO info2413;

--
-- TOC entry 231 (class 1259 OID 19232)
-- Name: Fine_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Fine_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Fine_SEQ" OWNER TO info2413;

--
-- TOC entry 252 (class 1259 OID 19361)
-- Name: Fine_fine_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Fine" ALTER COLUMN fine_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Fine_fine_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 244 (class 1259 OID 19279)
-- Name: Hold; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Hold"
(
    "account_id_Account"      integer,
    book_hold_id              integer NOT NULL,
    held_since                date,
    hold_expiry               date,
    "serial_barcode_BookCopy" integer NOT NULL
);


ALTER TABLE public."Hold" OWNER TO info2413;

--
-- TOC entry 232 (class 1259 OID 19233)
-- Name: Hold_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Hold_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Hold_SEQ" OWNER TO info2413;

--
-- TOC entry 255 (class 1259 OID 19390)
-- Name: Hold_book_hold_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Hold" ALTER COLUMN book_hold_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Hold_book_hold_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 245 (class 1259 OID 19284)
-- Name: LibraryCard; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."LibraryCard"
(
    "account_id_Account" integer,
    card_number          integer NOT NULL,
    valid                boolean NOT NULL
);


ALTER TABLE public."LibraryCard" OWNER TO info2413;

--
-- TOC entry 233 (class 1259 OID 19234)
-- Name: LibraryCard_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."LibraryCard_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."LibraryCard_SEQ" OWNER TO info2413;

--
-- TOC entry 234 (class 1259 OID 19235)
-- Name: Payment_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Payment_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Payment_SEQ" OWNER TO info2413;

--
-- TOC entry 253 (class 1259 OID 19362)
-- Name: Payment_payment_id_seq; Type: SEQUENCE; Schema: public; Owner: info2413
--

ALTER TABLE public."Payment" ALTER COLUMN payment_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Payment_payment_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 247 (class 1259 OID 19296)
-- Name: Publication; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."Publication"
(
    isbn_13          bigint NOT NULL,
    page_count       integer,
    publication_date date,
    genre            character varying(255),
    title            character varying(255),
    ebook_url        text
);


ALTER TABLE public."Publication" OWNER TO info2413;

--
-- TOC entry 248 (class 1259 OID 19303)
-- Name: PublicationAuthor; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."PublicationAuthor"
(
    "author_id_Author"    integer NOT NULL,
    "isbn_13_Publication" bigint  NOT NULL
);


ALTER TABLE public."PublicationAuthor" OWNER TO info2413;

--
-- TOC entry 249 (class 1259 OID 19308)
-- Name: PublicationPublication; Type: TABLE; Schema: public; Owner: info2413
--

CREATE TABLE public."PublicationPublication"
(
    isbn_13_publication  bigint NOT NULL,
    isbn_13_publication1 bigint NOT NULL
);


ALTER TABLE public."PublicationPublication" OWNER TO info2413;

--
-- TOC entry 235 (class 1259 OID 19236)
-- Name: Publication_SEQ; Type: SEQUENCE; Schema: public; Owner: info2413
--

CREATE SEQUENCE public."Publication_SEQ"
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE CACHE 1;


ALTER SEQUENCE public."Publication_SEQ" OWNER TO info2413;

--
-- TOC entry 3294 (class 2606 OID 19481)
-- Name: AccountPublication AccountPublication_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."AccountPublication"
    ADD CONSTRAINT "AccountPublication_pkey" PRIMARY KEY (account_id_account, isbn_13_publication);


--
-- TOC entry 3292 (class 2606 OID 19243)
-- Name: Account Account_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Account"
    ADD CONSTRAINT "Account_pkey" PRIMARY KEY (account_id);


--
-- TOC entry 3296 (class 2606 OID 19253)
-- Name: Author Author_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Author"
    ADD CONSTRAINT "Author_pkey" PRIMARY KEY (author_id);


--
-- TOC entry 3300 (class 2606 OID 19393)
-- Name: BookCopyConditionNotes BookCopyConditionNotes_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."BookCopyConditionNotes"
    ADD CONSTRAINT "BookCopyConditionNotes_pkey" PRIMARY KEY (note_id);


--
-- TOC entry 3298 (class 2606 OID 19258)
-- Name: BookCopy BookCopy_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."BookCopy"
    ADD CONSTRAINT "BookCopy_pkey" PRIMARY KEY (serial_barcode);


--
-- TOC entry 3304 (class 2606 OID 19273)
-- Name: BorrowRenewalDate BorrowRenewalDate_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."BorrowRenewalDate"
    ADD CONSTRAINT "BorrowRenewalDate_pkey" PRIMARY KEY (renewal_date);


--
-- TOC entry 3302 (class 2606 OID 19268)
-- Name: Borrow Borrow_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Borrow"
    ADD CONSTRAINT "Borrow_pkey" PRIMARY KEY (borrow_id);


--
-- TOC entry 3320 (class 2606 OID 19553)
-- Name: Configuration Configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Configuration"
    ADD CONSTRAINT "Configuration_pkey" PRIMARY KEY (config_key);


--
-- TOC entry 3306 (class 2606 OID 19278)
-- Name: Fine Fine_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Fine"
    ADD CONSTRAINT "Fine_pkey" PRIMARY KEY (fine_id);


--
-- TOC entry 3308 (class 2606 OID 19283)
-- Name: Hold Hold_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Hold"
    ADD CONSTRAINT "Hold_pkey" PRIMARY KEY (book_hold_id);


--
-- TOC entry 3310 (class 2606 OID 19288)
-- Name: LibraryCard LibraryCard_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."LibraryCard"
    ADD CONSTRAINT "LibraryCard_pkey" PRIMARY KEY (card_number);


--
-- TOC entry 3312 (class 2606 OID 19295)
-- Name: Payment Payment_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Payment"
    ADD CONSTRAINT "Payment_pkey" PRIMARY KEY (payment_id);


--
-- TOC entry 3316 (class 2606 OID 19448)
-- Name: PublicationAuthor PublicationAuthor_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationAuthor"
    ADD CONSTRAINT "PublicationAuthor_pkey" PRIMARY KEY ("author_id_Author", "isbn_13_Publication");


--
-- TOC entry 3318 (class 2606 OID 19470)
-- Name: PublicationPublication PublicationPublication_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationPublication"
    ADD CONSTRAINT "PublicationPublication_pkey" PRIMARY KEY (isbn_13_publication, isbn_13_publication1);


--
-- TOC entry 3314 (class 2606 OID 19415)
-- Name: Publication Publication_pkey; Type: CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."Publication"
    ADD CONSTRAINT "Publication_pkey" PRIMARY KEY (isbn_13);


--
-- TOC entry 3324 (class 2606 OID 19328)
-- Name: PublicationAuthor FK6vjvhpy84qpeq072t1x4gkgfj; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationAuthor"
    ADD CONSTRAINT "FK6vjvhpy84qpeq072t1x4gkgfj" FOREIGN KEY ("author_id_Author") REFERENCES public."Author"(author_id);


--
-- TOC entry 3326 (class 2606 OID 19515)
-- Name: PublicationPublication FKgx46po00oqqjq95x10s2nqyy4; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationPublication"
    ADD CONSTRAINT "FKgx46po00oqqjq95x10s2nqyy4" FOREIGN KEY (isbn_13_publication1) REFERENCES public."Publication"(isbn_13) ON
UPDATE CASCADE;


--
-- TOC entry 3327 (class 2606 OID 19520)
-- Name: PublicationPublication FKk40akwro2qvluqk3t4tmc8uwj; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationPublication"
    ADD CONSTRAINT "FKk40akwro2qvluqk3t4tmc8uwj" FOREIGN KEY (isbn_13_publication) REFERENCES public."Publication"(isbn_13) ON
UPDATE CASCADE;


--
-- TOC entry 3321 (class 2606 OID 19500)
-- Name: AccountPublication FKlhmejwq4esl2dxm5omf1tbf9k; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."AccountPublication"
    ADD CONSTRAINT "FKlhmejwq4esl2dxm5omf1tbf9k" FOREIGN KEY (isbn_13_publication) REFERENCES public."Publication"(isbn_13) ON
UPDATE CASCADE;


--
-- TOC entry 3323 (class 2606 OID 19505)
-- Name: BookCopy FKq8easj3y9slf97ll5s0v1d8u3; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."BookCopy"
    ADD CONSTRAINT "FKq8easj3y9slf97ll5s0v1d8u3" FOREIGN KEY ("isbn_13_Publication") REFERENCES public."Publication"(isbn_13) ON
UPDATE CASCADE;


--
-- TOC entry 3325 (class 2606 OID 19510)
-- Name: PublicationAuthor FKspa3uysge6d7i9j5walgm9dbe; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."PublicationAuthor"
    ADD CONSTRAINT "FKspa3uysge6d7i9j5walgm9dbe" FOREIGN KEY ("isbn_13_Publication") REFERENCES public."Publication"(isbn_13) ON
UPDATE CASCADE;


--
-- TOC entry 3322 (class 2606 OID 19313)
-- Name: AccountPublication FKspo9uvamlykxcbxw0mk88add9; Type: FK CONSTRAINT; Schema: public; Owner: info2413
--

ALTER TABLE ONLY public."AccountPublication"
    ADD CONSTRAINT "FKspo9uvamlykxcbxw0mk88add9" FOREIGN KEY (account_id_account) REFERENCES public."Account"(account_id);


-- Completed on 2025-11-30 15:19:39

--
-- PostgreSQL database dump complete
--

\unrestrict
F3yGhoBQhoBomshnajNToT2kNffum7LY1xbasIqbl4vcxaTq0qze3mn5AX7tJ7W

