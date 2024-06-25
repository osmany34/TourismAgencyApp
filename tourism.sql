PGDMP      	                |            tourism    14.12    16.3 5    %           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            &           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            '           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            (           1262    24933    tourism    DATABASE     |   CREATE DATABASE tourism WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE tourism;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            )           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4            �            1259    24963    hotel    TABLE     �  CREATE TABLE public.hotel (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    city character varying(100) NOT NULL,
    mail character varying(100) NOT NULL,
    phone character varying(20) NOT NULL,
    star character varying(10),
    wifi boolean,
    car_park boolean,
    pool boolean,
    fitness boolean,
    spa boolean,
    room_service boolean
);
    DROP TABLE public.hotel;
       public         heap    postgres    false    4            �            1259    24962    hotel_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.hotel_id_seq;
       public          postgres    false    4    210            *           0    0    hotel_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.hotel_id_seq OWNED BY public.hotel.id;
          public          postgres    false    209            �            1259    24972    pension    TABLE     �   CREATE TABLE public.pension (
    id integer NOT NULL,
    pension_type character varying(50) NOT NULL,
    hotel_id integer
);
    DROP TABLE public.pension;
       public         heap    postgres    false    4            �            1259    24971    pension_id_seq    SEQUENCE     �   CREATE SEQUENCE public.pension_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.pension_id_seq;
       public          postgres    false    4    212            +           0    0    pension_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.pension_id_seq OWNED BY public.pension.id;
          public          postgres    false    211            �            1259    24984    reservation    TABLE     �  CREATE TABLE public.reservation (
    id integer NOT NULL,
    room_id integer NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    total_price double precision NOT NULL,
    guest_count integer NOT NULL,
    guest_name character varying(255) NOT NULL,
    guess_citizen_id character varying(20) NOT NULL,
    guess_phone character varying(20) NOT NULL,
    guess_mail character varying(100) NOT NULL,
    adult_count integer,
    child_count integer
);
    DROP TABLE public.reservation;
       public         heap    postgres    false    4            �            1259    24983    reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.reservation_id_seq;
       public          postgres    false    214    4            ,           0    0    reservation_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.reservation_id_seq OWNED BY public.reservation.id;
          public          postgres    false    213            �            1259    25010    room    TABLE     �  CREATE TABLE public.room (
    id integer NOT NULL,
    hotel_id integer,
    pension_id integer,
    season_id integer,
    type character varying(50) NOT NULL,
    stock integer NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL,
    bed_capacity integer NOT NULL,
    square_meter integer NOT NULL,
    television boolean,
    minibar boolean,
    iron boolean,
    air boolean,
    cafe boolean
);
    DROP TABLE public.room;
       public         heap    postgres    false    4            �            1259    25009    room_id_seq    SEQUENCE     �   CREATE SEQUENCE public.room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.room_id_seq;
       public          postgres    false    220    4            -           0    0    room_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.room_id_seq OWNED BY public.room.id;
          public          postgres    false    219            �            1259    24998    season    TABLE     �   CREATE TABLE public.season (
    id integer NOT NULL,
    hotel_id integer,
    start_date date NOT NULL,
    finish_date date NOT NULL,
    season_type character varying(50) NOT NULL,
    price_parameter double precision NOT NULL
);
    DROP TABLE public.season;
       public         heap    postgres    false    4            �            1259    24997    season_id_seq    SEQUENCE     �   CREATE SEQUENCE public.season_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.season_id_seq;
       public          postgres    false    218    4            .           0    0    season_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.season_id_seq OWNED BY public.season.id;
          public          postgres    false    217            �            1259    24991    user    TABLE     �   CREATE TABLE public."user" (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(50) NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false    4            �            1259    24990    user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.user_id_seq;
       public          postgres    false    216    4            /           0    0    user_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;
          public          postgres    false    215            u           2604    24966    hotel id    DEFAULT     d   ALTER TABLE ONLY public.hotel ALTER COLUMN id SET DEFAULT nextval('public.hotel_id_seq'::regclass);
 7   ALTER TABLE public.hotel ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209    210            v           2604    24975 
   pension id    DEFAULT     h   ALTER TABLE ONLY public.pension ALTER COLUMN id SET DEFAULT nextval('public.pension_id_seq'::regclass);
 9   ALTER TABLE public.pension ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    211    212    212            w           2604    24987    reservation id    DEFAULT     p   ALTER TABLE ONLY public.reservation ALTER COLUMN id SET DEFAULT nextval('public.reservation_id_seq'::regclass);
 =   ALTER TABLE public.reservation ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    213    214    214            z           2604    25013    room id    DEFAULT     b   ALTER TABLE ONLY public.room ALTER COLUMN id SET DEFAULT nextval('public.room_id_seq'::regclass);
 6   ALTER TABLE public.room ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            y           2604    25001 	   season id    DEFAULT     f   ALTER TABLE ONLY public.season ALTER COLUMN id SET DEFAULT nextval('public.season_id_seq'::regclass);
 8   ALTER TABLE public.season ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218            x           2604    24994    user id    DEFAULT     d   ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);
 8   ALTER TABLE public."user" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215    216                      0    24963    hotel 
   TABLE DATA           }   COPY public.hotel (id, name, address, city, mail, phone, star, wifi, car_park, pool, fitness, spa, room_service) FROM stdin;
    public          postgres    false    210   =                 0    24972    pension 
   TABLE DATA           =   COPY public.pension (id, pension_type, hotel_id) FROM stdin;
    public          postgres    false    212   >                 0    24984    reservation 
   TABLE DATA           �   COPY public.reservation (id, room_id, check_in_date, check_out_date, total_price, guest_count, guest_name, guess_citizen_id, guess_phone, guess_mail, adult_count, child_count) FROM stdin;
    public          postgres    false    214   �>       "          0    25010    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, pension_id, season_id, type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, iron, air, cafe) FROM stdin;
    public          postgres    false    220   A                  0    24998    season 
   TABLE DATA           e   COPY public.season (id, hotel_id, start_date, finish_date, season_type, price_parameter) FROM stdin;
    public          postgres    false    218   7B                 0    24991    user 
   TABLE DATA           >   COPY public."user" (id, username, password, role) FROM stdin;
    public          postgres    false    216   �B       0           0    0    hotel_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.hotel_id_seq', 6, true);
          public          postgres    false    209            1           0    0    pension_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pension_id_seq', 6, true);
          public          postgres    false    211            2           0    0    reservation_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.reservation_id_seq', 15, true);
          public          postgres    false    213            3           0    0    room_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.room_id_seq', 6, true);
          public          postgres    false    219            4           0    0    season_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.season_id_seq', 6, true);
          public          postgres    false    217            5           0    0    user_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.user_id_seq', 8, true);
          public          postgres    false    215            |           2606    24970    hotel hotel_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    210            ~           2606    24977    pension pension_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    212            �           2606    24989    reservation reservation_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    214            �           2606    25015    room room_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    220            �           2606    25003    season season_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    218            �           2606    24996    user user_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    216            �           2606    24978    pension pension_hotel_id_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(id);
 G   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_hotel_id_fkey;
       public          postgres    false    3196    212    210            �           2606    25016    room room_hotel_id_fkey    FK CONSTRAINT     w   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(id);
 A   ALTER TABLE ONLY public.room DROP CONSTRAINT room_hotel_id_fkey;
       public          postgres    false    3196    210    220            �           2606    25021    room room_pension_id_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pension_id_fkey FOREIGN KEY (pension_id) REFERENCES public.pension(id);
 C   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pension_id_fkey;
       public          postgres    false    3198    212    220            �           2606    25026    room room_season_id_fkey    FK CONSTRAINT     z   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_season_id_fkey FOREIGN KEY (season_id) REFERENCES public.season(id);
 B   ALTER TABLE ONLY public.room DROP CONSTRAINT room_season_id_fkey;
       public          postgres    false    218    3204    220            �           2606    25004    season season_hotel_id_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(id);
 E   ALTER TABLE ONLY public.season DROP CONSTRAINT season_hotel_id_fkey;
       public          postgres    false    210    218    3196               �   x�u�Mn� ���)X[J�`H�]�n"�U/�ͤ&
2��Rȩ�;�`��-
ͼ�O�a_Nj���ͩ��G�Ԩ�;��L5e��Ն�*)�{�۲U��j����4	��7Sb�p��{�j��S�M�B��d|䑛Ϲ|�������h������$MV�4�������7U�p����1�#�ԧ���D��`������A-�M�")��",Y�0]܉Z�P{��Ж�Kqֶ��\��;�~,�}�	!L���         p   x�3��H-R8:/�R�%1#3�Ӑˈ32����\��ļ����<N#.cΐD$c.N��D�Č�Ĝ�#9M�L9�SR�S���\f��
�.�
ގa�>!���@]1z\\\ <K!h         h  x�uT;��0�W��D��O#p\� a�0a����"_ 7H���ݧ�߽����^c��rgf̀�\$4O�̱�8,�S6~��v� ��BfyQ"�ʔ'�H|��JS��j�~p�Sq��(J	L"{
tmU��7��rްc#��6agQ�e1�Kd/�]��\��Vş�W�.0H�	�H|\����@���ߠH8�2��E��/*�0Ћ����%�fI�-g9�P�yhu>�S|�q��Z6��H��cf�d����_N$.��9}
�8u���~�Q�64�'�|�GN�!����//U��,�L
������4�#b�FE˚d~��HX����q"�H�\ ���3�P�h�o�p\ީ�S�����)�["��̜Ʒw�g4�ӕ�95�ɚ�zi�9�mQ֐'�������r|v��~�Y�}���a�*�[�C��������d�M3�;�%n�Z�9����tm��u��~Oo[v���H�ƚ�\���7ޤE{k�v8�!�|��p�����L#��	`�w�sƗx�m ��.^쫇z��p'}����s���]��S�K	mD��Q?��VǑ��Dt#*!
!�i��DQ��4n]      "   #  x�}�Mj�0�ףS�a$Y��o����n�Zi����;��ƆP[|���F��z�c�Y��APP!�"� ����w�'�Ȩ���!���`�0B5�ۊ$�v�0��P?�%+z�)g ����nZ	�M���}/x�^�a����\��مO�>6�ŉ/�s6R���h$�l�wB���|J��/��F��a1-HˡNf��4����dGf�z�rޒ�[��e��2NZ�@\�y_�u���n$��h���n�D��p�lc�H]�-\�R�Og�&�+���.E�� ����          x   x�}���@D��^���{g�5!99"$��i��� �!#��|�ͳZ���^���+�\���F5Wﱙ�ۛ��E�o�a؞�}=�,���������Q
��K!c�˒&_         O   x�3�LL���3D���8Ssr�+SS����8R����Rs����!��P\&pFXX\�p�1��"lL�=... (`Ee     