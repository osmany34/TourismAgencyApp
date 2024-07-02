PGDMP  '                    |            tourism    14.12    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    25067    tourism    DATABASE     |   CREATE DATABASE tourism WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE tourism;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4            �            1259    25068    hotel    TABLE     �  CREATE TABLE public.hotel (
    id bigint NOT NULL,
    name character varying NOT NULL,
    address character varying NOT NULL,
    city character varying(255) NOT NULL,
    mail character varying NOT NULL,
    phone character varying NOT NULL,
    star character varying,
    car_park boolean,
    wifi boolean,
    pool boolean,
    fitness boolean,
    spa boolean,
    room_service boolean
);
    DROP TABLE public.hotel;
       public         heap    postgres    false    4            �            1259    25073    hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 789746567864
    CACHE 1
);
            public          postgres    false    209    4            �            1259    25074    hotel_pension    TABLE     �   CREATE TABLE public.hotel_pension (
    id bigint NOT NULL,
    hotel_id integer NOT NULL,
    pension_type character varying NOT NULL
);
 !   DROP TABLE public.hotel_pension;
       public         heap    postgres    false    4            �            1259    25079    hotel_pension_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_pension ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    211    4            �            1259    25080    hotel_season    TABLE     �   CREATE TABLE public.hotel_season (
    id bigint NOT NULL,
    hotel_id integer NOT NULL,
    start_date date NOT NULL,
    finish_date date NOT NULL,
    price_parameter double precision NOT NULL
);
     DROP TABLE public.hotel_season;
       public         heap    postgres    false    4            �            1259    25083    hotel_season_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_season ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    213    4            �            1259    25096    reservation    TABLE     L  CREATE TABLE public.reservation (
    id bigint NOT NULL,
    room_id integer,
    check_in_date date,
    total_price double precision,
    guest_count integer,
    guest_name character varying,
    guest_citizen_id character varying,
    guest_mail character varying,
    guest_phone character varying,
    check_out_date date
);
    DROP TABLE public.reservation;
       public         heap    postgres    false    4            �            1259    25101    reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    219            �            1259    25084    room    TABLE       CREATE TABLE public.room (
    id bigint NOT NULL,
    hotel_id integer NOT NULL,
    pension_id integer NOT NULL,
    season_id integer NOT NULL,
    type character varying NOT NULL,
    stock integer NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL,
    bed_capacity integer NOT NULL,
    square_meter integer NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    cash_box boolean NOT NULL,
    projection boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false    4            �            1259    25089    room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    215            �            1259    25090    users    TABLE     w   CREATE TABLE public.users (
    user_id bigint NOT NULL,
    user_name text,
    user_pass text,
    user_role text
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            �            1259    25095    user_user_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217    4                       0    25068    hotel 
   TABLE DATA           }   COPY public.hotel (id, name, address, city, mail, phone, star, car_park, wifi, pool, fitness, spa, room_service) FROM stdin;
    public          postgres    false    209   #                 0    25074    hotel_pension 
   TABLE DATA           C   COPY public.hotel_pension (id, hotel_id, pension_type) FROM stdin;
    public          postgres    false    211   �#                 0    25080    hotel_season 
   TABLE DATA           ^   COPY public.hotel_season (id, hotel_id, start_date, finish_date, price_parameter) FROM stdin;
    public          postgres    false    213   �$       
          0    25096    reservation 
   TABLE DATA           �   COPY public.reservation (id, room_id, check_in_date, total_price, guest_count, guest_name, guest_citizen_id, guest_mail, guest_phone, check_out_date) FROM stdin;
    public          postgres    false    219   �$                 0    25084    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, pension_id, season_id, type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, game_console, cash_box, projection) FROM stdin;
    public          postgres    false    215   i%                 0    25090    users 
   TABLE DATA           I   COPY public.users (user_id, user_name, user_pass, user_role) FROM stdin;
    public          postgres    false    217   {&                  0    0    hotel_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.hotel_id_seq', 16, true);
          public          postgres    false    210                       0    0    hotel_pension_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.hotel_pension_id_seq', 44, true);
          public          postgres    false    212                       0    0    hotel_season_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hotel_season_id_seq', 56, true);
          public          postgres    false    214                       0    0    reservation_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.reservation_id_seq', 7, true);
          public          postgres    false    220                       0    0    room_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.room_id_seq', 12, true);
          public          postgres    false    216                       0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 8, true);
          public          postgres    false    218                �   x�E�An�0E��S��xjGQWI���R�e7�c*��\&g�>��{u
H�5�_�y~V4^���jǫ|wmh"|�o'��+���Q�5<uMK`��d��DE�씩5�ub�Ip��!�n����d�O��ZQ�Q����s���䳎���6��*D��6�5��P��7�~.��p�sY�=�&i��c۪��Xt����EX�$�=f����Y�Z�         �   x�m�A
1ϙW�]c��5;$�Jŧ���3��(���j��n̂O�f�(Ǣ���� �5�7Z1�L�����zA�o�W����ӂ izES��?�뿐���~�|�Y���]�8b<#U-J�%�2i         Q   x�}�Q
�0�os��*�.��9�~����'J��1�J�G-ujwq���Gc���l���;�u�jl5�]/�uף�� \�)      
   q   x�uNA
�0;�و�(��.���`����9��^f	�@��Ë�[a]���I]}�^�y�Ό2cy�(h���D�9b�-����ǿ1�[�c+�5�j�ϻ�}��+E           x�e�Mj�0���)r�b�?I�=A3�nR&)&�Jz�^dv��꓉'�����'EH�y���H�:k�kQ���c�'���r>6bK� Y7i�Z�v�r`q�_4��A��҆;(,�3o�CDS��
��p2�
��� �k�G�H~@���el��������nb����t���V:Zr�&ɮud&��.�O'E��Y�Gj5Qa�5ԩ�NǺ��H�9�ٙ�2��{�fN?_iIs����Q*cj�˃1���n�         K   x�3�L�-�ɯLM�4426��9Sr3� $P
��2��/�M��4�40�
Yp�$�Pq1gq"�m������� Bf     