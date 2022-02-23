package com.anahorn.fukusizer;

import android.content.Context;

public class Data {
    public static void InitializeSizes(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.onCreate(db.getWritableDatabase());

        // Inserting sizes

        // Women
        db.addContact(new Contact("Women", "Tops", "USA:XXXS,USA:000 (30.5 inches),UK:0,Germany:24,France:28,Italy:32,Spain:26,Japan:1,Australia:0,"));
        db.addContact(new Contact("Women", "Tops", "USA:XXS,USA:00 (31.5 inches),UK:2,Germany:26,France:30,Italy:34,Spain:28,Japan:1,Australia:2,"));
        db.addContact(new Contact("Women", "Tops", "USA:XS,USA:0 (32.5 inches),UK:4,Germany:28,France:32,Italy:36,Spain:30,Japan:3,Australia:4,"));
        db.addContact(new Contact("Women", "Tops", "USA:XS,USA:2 (33.5 inches),UK:6,Germany:30,France:34,Italy:38,Spain:32,Japan:5,Australia:6,"));
        db.addContact(new Contact("Women", "Tops", "USA:S,USA:4 (34.5 inches),UK:8,Germany:32,France:36,Italy:40,Spain:34,Japan:7,Australia:8,"));
        db.addContact(new Contact("Women", "Tops", "USA:S,USA:6 (35.5 inches),UK:10,Germany:34,France:38,Italy:42,Spain:36,Japan:9,Australia:10,"));
        db.addContact(new Contact("Women", "Tops", "USA:M,USA:8 (36.5 inches),UK:12,Germany:36,France:40,Italy:44,Spain:38,Japan:11,Australia:12,"));
        db.addContact(new Contact("Women", "Tops", "USA:M,USA:10 (37.5 inches),UK:14,Germany:38,France:42,Italy:46,Spain:40,Japan:13,Australia:14,"));
        db.addContact(new Contact("Women", "Tops", "USA:L,USA:12 (39 inches),UK:16,Germany:40,France:44,Italy:48,Spain:42,Japan:15,Australia:16,"));
        db.addContact(new Contact("Women", "Tops", "USA:L,USA:14 (40.5 inches),UK:18,Germany:42,France:46,Italy:50,Spain:44,Japan:17,Australia:18,"));
        db.addContact(new Contact("Women", "Tops", "USA:XL,USA:16 (42 inches),UK:20,Germany:44,France:48,Italy:52,Spain:46,Japan:19,Australia:20,"));
        db.addContact(new Contact("Women", "Tops", "USA:XXL,USA:18 (43.5 inches),UK:22,Germany:46,France:50,Italy:54,Spain:48,Japan:21,Australia:22,"));
        db.addContact(new Contact("Women", "Tops", "USA:XXL,USA:20 (43.5 inches),UK:24,Germany:48,France:52,Italy:56,Spain:50,Japan:23,Australia:24,"));

        db.addContact(new Contact("Women", "Bottoms", "USA:XXXS,USA:000,UK:0,Germany:24,France:28,Italy:32,Spain:26,Japan:1,Australia:0,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XXS,USA:00,UK:2,Germany:26,France:30,Italy:34,Spain:28,Japan:1,Australia:2,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XS,USA:0,UK:4,Germany:28,France:32,Italy:36,Spain:30,Japan:3,Australia:4,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XS,USA:2,UK:6,Germany:30,France:34,Italy:38,Spain:32,Japan:5,Australia:6,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:S,USA:4,UK:8,Germany:32,France:36,Italy:40,Spain:34,Japan:7,Australia:8,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:S,USA:6,UK:10,Germany:34,France:38,Italy:42,Spain:36,Japan:9,Australia:10,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:M,USA:8,UK:12,Germany:36,France:40,Italy:44,Spain:38,Japan:11,Australia:12,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:M,USA:10,UK:14,Germany:38,France:42,Italy:46,Spain:40,Japan:13,Australia:14,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:L,USA:12,UK:16,Germany:40,France:44,Italy:48,Spain:42,Japan:15,Australia:16,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:L,USA:14,UK:18,Germany:42,France:46,Italy:50,Spain:44,Japan:17,Australia:18,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XL,USA:16,UK:20,Germany:44,France:48,Italy:52,Spain:46,Japan:19,Australia:20,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XL,USA:18,UK:22,Germany:46,France:50,Italy:54,Spain:48,Japan:21,Australia:22,"));
        db.addContact(new Contact("Women", "Bottoms", "USA:XXL,USA:20,UK:24,Germany:48,France:52,Italy:56,Spain:50,Japan:23,Australia:24,"));

        db.addContact(new Contact("Women", "Swimwear", "USA:XXXS,USA:000,UK:0,Germany:24,France:28,Italy:32,Spain:26,Japan:1,Australia:0,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XXS,USA:00,UK:2,Germany:26,France:30,Italy:34,Spain:28,Japan:1,Australia:2,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XS,USA:0,UK:4,Germany:28,France:32,Italy:36,Spain:30,Japan:3,Australia:4,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XS,USA:2,UK:6,Germany:30,France:34,Italy:38,Spain:32,Japan:5,Australia:6,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:S,USA:4,UK:8,Germany:32,France:36,Italy:40,Spain:34,Japan:7,Australia:8,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:S,USA:6,UK:10,Germany:34,France:38,Italy:42,Spain:36,Japan:9,Australia:10,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:M,USA:8,UK:12,Germany:36,France:40,Italy:44,Spain:38,Japan:11,Australia:12,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:M,USA:10,UK:14,Germany:38,France:42,Italy:46,Spain:40,Japan:13,Australia:14,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:L,USA:12,UK:16,Germany:40,France:44,Italy:48,Spain:42,Japan:15,Australia:16,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:L,USA:14,UK:18,Germany:42,France:46,Italy:50,Spain:44,Japan:17,Australia:18,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XL,USA:16,UK:20,Germany:44,France:48,Italy:52,Spain:46,Japan:19,Australia:20,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XL,USA:18,UK:22,Germany:46,France:50,Italy:54,Spain:48,Japan:21,Australia:22,"));
        db.addContact(new Contact("Women", "Swimwear", "USA:XXL,USA:20,UK:24,Germany:48,France:52,Italy:56,Spain:50,Japan:23,Australia:24,"));

        db.addContact(new Contact("Women", "Dresses", "USA:XXXS,USA:000,UK:0,Germany:24,France:28,Italy:32,Spain:26,Japan:1,Australia:0,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XXS,USA:00,UK:2,Germany:26,France:30,Italy:34,Spain:28,Japan:1,Australia:2,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XS,USA:0,UK:4,Germany:28,France:32,Italy:36,Spain:30,Japan:3,Australia:4,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XS,USA:2,UK:6,Germany:30,France:34,Italy:38,Spain:32,Japan:5,Australia:6,"));
        db.addContact(new Contact("Women", "Dresses", "USA:S,USA:4,UK:8,Germany:32,France:36,Italy:40,Spain:34,Japan:7,Australia:8,"));
        db.addContact(new Contact("Women", "Dresses", "USA:S,USA:6,UK:10,Germany:34,France:38,Italy:42,Spain:36,Japan:9,Australia:10,"));
        db.addContact(new Contact("Women", "Dresses", "USA:M,USA:8,UK:12,Germany:36,France:40,Italy:44,Spain:38,Japan:11,Australia:12,"));
        db.addContact(new Contact("Women", "Dresses", "USA:M,USA:10,UK:14,Germany:38,France:42,Italy:46,Spain:40,Japan:13,Australia:14,"));
        db.addContact(new Contact("Women", "Dresses", "USA:L,USA:12,UK:16,Germany:40,France:44,Italy:48,Spain:42,Japan:15,Australia:16,"));
        db.addContact(new Contact("Women", "Dresses", "USA:L,USA:14,UK:18,Germany:42,France:46,Italy:50,Spain:44,Japan:17,Australia:18,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XL,USA:16,UK:20,Germany:44,France:48,Italy:52,Spain:46,Japan:19,Australia:20,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XL,USA:18,UK:22,Germany:46,France:50,Italy:54,Spain:48,Japan:21,Australia:22,"));
        db.addContact(new Contact("Women", "Dresses", "USA:XXL,USA:20,UK:24,Germany:48,France:52,Italy:56,Spain:50,Japan:23,Australia:24,"));

        db.addContact(new Contact("Women", "Outerwear", "USA:XXXS,USA:000,UK:0,Germany:24,France:28,Italy:32,Spain:26,Japan:1,Australia:0,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XXS,USA:00,UK:2,Germany:26,France:30,Italy:34,Spain:28,Japan:1,Australia:2,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XS,USA:0,UK:4,Germany:28,France:32,Italy:36,Spain:30,Japan:3,Australia:4,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XS,USA:2,UK:6,Germany:30,France:34,Italy:38,Spain:32,Japan:5,Australia:6,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:S,USA:4,UK:8,Germany:32,France:36,Italy:40,Spain:34,Japan:7,Australia:8,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:S,USA:6,UK:10,Germany:34,France:38,Italy:42,Spain:36,Japan:9,Australia:10,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:M,USA:8,UK:12,Germany:36,France:40,Italy:44,Spain:38,Japan:11,Australia:12,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:M,USA:10,UK:14,Germany:38,France:42,Italy:46,Spain:40,Japan:13,Australia:14,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:L,USA:12,UK:16,Germany:40,France:44,Italy:48,Spain:42,Japan:15,Australia:16,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:L,USA:14,UK:18,Germany:42,France:46,Italy:50,Spain:44,Japan:17,Australia:18,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XL,USA:16,UK:20,Germany:44,France:48,Italy:52,Spain:46,Japan:19,Australia:20,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XL,USA:18,UK:22,Germany:46,France:50,Italy:54,Spain:48,Japan:21,Australia:22,"));
        db.addContact(new Contact("Women", "Outerwear", "USA:XXL,USA:20,UK:24,Germany:48,France:52,Italy:56,Spain:50,Japan:23,Australia:24,"));

        db.addContact(new Contact("Women", "Shoes", "USA:5,UK:2.5,Europe:35.5,Japan:21.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:5H,USA:5.5,UK:3,Europe:36,Japan:22,"));
        db.addContact(new Contact("Women", "Shoes", "USA:6,UK:3.5,Europe:36.5,Japan:22.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:6H,USA:6.5,UK:4,Europe:37,Japan:23,"));
        db.addContact(new Contact("Women", "Shoes", "USA:7,UK:4.5,Europe:37.5,Japan:23.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:7H,USA:7.5,UK:5,Europe:38,Japan:24,"));
        db.addContact(new Contact("Women", "Shoes", "USA:8,UK:5.5,Europe:38.5,Japan:24.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:8H,USA:8.5,UK:6,Europe:39,Japan:25,"));
        db.addContact(new Contact("Women", "Shoes", "USA:9,UK:6.5,Europe:39.5,Japan:25.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:9H,USA:9.5,UK:7,Europe:40,Japan:26,"));
        db.addContact(new Contact("Women", "Shoes", "USA:10,UK:7.5,Europe:40.5,Japan:26.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:10H,USA:10.5,UK:8,Europe:41,Japan:27,"));
        db.addContact(new Contact("Women", "Shoes", "USA:11,UK:8.5,Europe:41.5,Japan:27.5,"));
        db.addContact(new Contact("Women", "Shoes", "USA:11H,USA:11.5,UK:9,Europe:42,Japan:28,"));
        db.addContact(new Contact("Women", "Shoes", "USA:12,UK:9.5,Europe:42.5,Japan:28.5,"));

        db.addContact(new Contact("Women", "Bra", "Australia:8AA,New Zealand:8AA,USA:30AA,UK:30A,India:30A,Europe:65A,China:65A,Japan:65A,Korea:65A,France:80A,Spain:80A,Belgium:80A,"));
        db.addContact(new Contact("Women", "Bra", "Australia:8A,New Zealand:8A,USA:30A,UK:30B,India:30B,Europe:65B,China:65B,Japan:65B,Korea:65B,France:80B,Spain:80B,Belgium:80B,"));
        db.addContact(new Contact("Women", "Bra", "Australia:8B,New Zealand:8B,USA:30B,UK:30C,India:30C,Europe:65C,China:65C,Japan:65C,Korea:65C,France:80C,Spain:80C,Belgium:80C,"));
        db.addContact(new Contact("Women", "Bra", "Australia:8C,New Zealand:8C,USA:30C,UK:30D,India:30D,Europe:65D,China:65D,Japan:65D,Korea:65D,France:80D,Spain:80D,Belgium:80D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:8D,New Zealand:8D,USA:30D,UK:30DD,India:30DD,Europe:65E,China:65E,Japan:65E,Korea:65E,France:80E,Spain:80E,Belgium:80E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:8DD,New Zealand:8DD,USA:30DD,UK:30E,India:30E,Europe:65F,China:65F,Japan:65F,Korea:65F,France:80F,Spain:80F,Belgium:80F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10AA,New Zealand:10AA,USA:32AA,UK:32A,India:32A,Europe:70A,China:70A,Japan:70A,Korea:70A,France:85A,Spain:85A,Belgium:85A,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10A,New Zealand:10A,USA:32A,UK:32B,India:32B,Europe:70B,China:70B,Japan:70B,Korea:70B,France:85B,Spain:85B,Belgium:85B,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10B,New Zealand:10B,USA:32B,UK:32C,India:32C,Europe:70C,China:70C,Japan:70C,Korea:70C,France:85C,Spain:85C,Belgium:85C,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10C,New Zealand:10C,USA:32C,UK:32D,India:32D,Europe:70D,China:70D,Japan:70D,Korea:70D,France:85D,Spain:85D,Belgium:85D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10D,New Zealand:10D,USA:32D,UK:32DD,India:32DD,Europe:70E,China:70E,Japan:70E,Korea:70E,France:85E,Spain:85E,Belgium:85E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10DD,New Zealand:10DD,USA:32DD,UK:32E,India:32E,Europe:70F,China:70F,Japan:70F,Korea:70F,France:85F,Spain:85F,Belgium:85F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10E,New Zealand:10E,USA:32DDD/F,UK:32F,India:32F,Europe:70G,China:70G,Japan:70G,Korea:70G,France:85G,Spain:85G,Belgium:85G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10F,New Zealand:10F,USA:32F,UK:32G,India:32G,Europe:70H,China:70H,Japan:70H,Korea:70H,France:85H,Spain:85H,Belgium:85H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:10G,New Zealand:10G,USA:32G,UK:32H,India:32H,Europe:70I,China:70I,Japan:70I,Korea:70I,France:85I,Spain:85I,Belgium:85I,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12AA,New Zealand:12AA,USA:34AA,UK:34A,India:34A,Europe:75A,China:75A,Japan:75A,Korea:75A,France:90A,Spain:90A,Belgium:90A,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12A,New Zealand:12A,USA:34A,UK:34B,India:34B,Europe:75B,China:75B,Japan:75B,Korea:75B,France:90B,Spain:90B,Belgium:90B,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12B,New Zealand:12B,USA:34B,UK:34C,India:34C,Europe:75C,China:75C,Japan:75C,Korea:75C,France:90C,Spain:90C,Belgium:90C,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12C,New Zealand:12C,USA:34C,UK:34D,India:34D,Europe:75D,China:75D,Japan:75D,Korea:75D,France:90D,Spain:90D,Belgium:90D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12D,New Zealand:12D,USA:34D,UK:34DD,India:34DD,Europe:75E,China:75E,Japan:75E,Korea:75E,France:90E,Spain:90E,Belgium:90E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12DD,New Zealand:12DD,USA:34DD,UK:34E,India:34E,Europe:75F,China:75F,Japan:75F,Korea:75F,France:90F,Spain:90F,Belgium:90F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12E,New Zealand:12E,USA:34DDD/E,UK:34F,India:34F,Europe:75G,China:75G,Japan:75G,Korea:75G,France:90G,Spain:90G,Belgium:90G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12F,New Zealand:12F,USA:34F,UK:34G,India:34G,Europe:75H,China:75H,Japan:75H,Korea:75H,France:90H,Spain:90H,Belgium:90H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:12G,New Zealand:12G,USA:34G,UK:34H,India:34H,Europe:75I,China:75I,Japan:75I,Korea:75I,France:90I,Spain:90I,Belgium:90I,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14A,New Zealand:14A,USA:36A,UK:36B,India:36B,Europe:80B,China:80B,Japan:80B,Korea:80B,France:95B,Spain:95B,Belgium:95B,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14B,New Zealand:14B,USA:36B,UK:36C,India:36C,Europe:80C,China:80C,Japan:80C,Korea:80C,France:95C,Spain:95C,Belgium:95C,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14C,New Zealand:14C,USA:36C,UK:36D,India:36D,Europe:80D,China:80D,Japan:80D,Korea:80D,France:95D,Spain:95D,Belgium:95D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14D,New Zealand:14D,USA:36D,UK:36DD,India:36DD,Europe:80E,China:80E,Japan:80E,Korea:80E,France:95E,Spain:95E,Belgium:95E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14DD,New Zealand:14DD,USA:36DD,UK:36E,India:36E,Europe:80F,China:80F,Japan:80F,Korea:80F,France:95F,Spain:95F,Belgium:95F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14E,New Zealand:14E,USA:36DDD/E,UK:36F,India:36F,Europe:80G,China:80G,Japan:80G,Korea:80G,France:95G,Spain:95G,Belgium:95G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14F,New Zealand:14F,USA:36F,UK:36G,India:36G,Europe:80H,China:80H,Japan:80H,Korea:80H,France:95H,Spain:95H,Belgium:95H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:14G,New Zealand:14G,USA:36G,UK:36H,India:36H,Europe:80I,China:80I,Japan:80I,Korea:80I,France:95I,Spain:95I,Belgium:95I,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16B,New Zealand:16B,USA:38B,UK:38C,India:38C,Europe:85C,China:85C,Japan:85C,Korea:85C,France:100C,Spain:100C,Belgium:100C,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16C,New Zealand:16C,USA:38C,UK:38D,India:38D,Europe:85D,China:85D,Japan:85D,Korea:85D,France:100D,Spain:100D,Belgium:100D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16D,New Zealand:16D,USA:38D,UK:38DD,India:38DD,Europe:85E,China:85E,Japan:85E,Korea:85E,France:100E,Spain:100E,Belgium:100E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16DD,New Zealand:16DD,USA:38DD,UK:38E,India:38E,Europe:85F,China:85F,Japan:85F,Korea:85F,France:100F,Spain:100F,Belgium:100F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16E,New Zealand:16E,USA:38DDD/E,UK:38F,India:38F,Europe:85G,China:85G,Japan:85G,Korea:85G,France:100G,Spain:100G,Belgium:100G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:16F,New Zealand:16F,USA:38F,UK:38G,India:38G,Europe:85H,China:85H,Japan:85H,Korea:85H,France:100H,Spain:100H,Belgium:100H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18C,New Zealand:18C,USA:40C,UK:40D,India:40D,Europe:90D,China:90D,Japan:90D,Korea:90D,France:105D,Spain:105D,Belgium:105D,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18D,New Zealand:18D,USA:40D,UK:40DD,India:40DD,Europe:90E,China:90E,Japan:90E,Korea:90E,France:105E,Spain:105E,Belgium:105E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18DD,New Zealand:18DD,USA:40DD,UK:40E,India:40E,Europe:90F,China:90F,Japan:90F,Korea:90F,France:105F,Spain:105F,Belgium:105F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18E,New Zealand:18E,USA:40DDD/E,UK:40F,India:40F,Europe:90G,China:90G,Japan:90G,Korea:90G,France:105G,Spain:105G,Belgium:105G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18F,New Zealand:18F,USA:40F,UK:40G,India:40G,Europe:90H,China:90H,Japan:90H,Korea:90H,France:105H,Spain:105H,Belgium:105H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:18G,New Zealand:18G,USA:40G,UK:40H,India:40H,Europe:90I,China:90I,Japan:90I,Korea:90I,France:105I,Spain:105I,Belgium:105I,"));
        db.addContact(new Contact("Women", "Bra", "Australia:20D,New Zealand:20D,USA:42C,UK:42DD,India:42DD,Europe:95E,China:95E,Japan:95E,Korea:95E,France:110E,Spain:110E,Belgium:110E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:20DD,New Zealand:20DD,USA:42DD,UK:42E,India:42E,Europe:95F,China:95F,Japan:95F,Korea:95F,France:110F,Spain:110F,Belgium:110F,"));
        db.addContact(new Contact("Women", "Bra", "Australia:20E,New Zealand:20E,USA:42DDD/E,UK:42F,India:42F,Europe:95G,China:95G,Japan:95G,Korea:95G,France:110G,Spain:110G,Belgium:110G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:20F,New Zealand:20F,USA:42F,UK:42G,India:42G,Europe:95H,China:95H,Japan:95H,Korea:95H,France:110H,Spain:110H,Belgium:110H,"));
        db.addContact(new Contact("Women", "Bra", "Australia:20G,New Zealand:20G,USA:42G,UK:42H,India:42H,Europe:95I,China:95I,Japan:95I,Korea:95I,France:110I,Spain:110I,Belgium:110I,"));
        db.addContact(new Contact("Women", "Bra", "Australia:22D,New Zealand:22D,USA:44D,UK:44DD,India:44DD,Europe:100E,China:100E,Japan:100E,Korea:100E,France:115E,Spain:115E,Belgium:115E,"));
        db.addContact(new Contact("Women", "Bra", "Australia:22E,New Zealand:22E,USA:44DDD/E,UK:44F,India:44F,Europe:100G,China:100G,Japan:100G,Korea:100G,France:115G,Spain:115G,Belgium:115G,"));
        db.addContact(new Contact("Women", "Bra", "Australia:22F,New Zealand:22F,USA:44F,UK:44G,India:44G,Europe:100H,China:100H,Japan:100H,Korea:100H,France:115H,Spain:115H,Belgium:115H,"));

        db.addContact(new Contact("Women", "Panties", "USA:XS,USA:0-2,Europe:36,UK:8,Italy:2,France:38,Spain:38,Australia:10,New Zealand:10,Japan:S,"));
        db.addContact(new Contact("Women", "Panties", "USA:S,USA:4-6,Europe:38,UK:10,Italy:3,France:40,Spain:40,Australia:12,New Zealand:12,Japan:M,"));
        db.addContact(new Contact("Women", "Panties", "USA:M,USA:6-8,Europe:40,UK:12,Italy:4,France:42,Spain:42,Australia:14,New Zealand:14,Japan:M,"));
        db.addContact(new Contact("Women", "Panties", "USA:LG,USA:8-10,Europe:42,UK:14,Italy:5,France:44,Spain:44,Australia:16,New Zealand:16,Japan:L,"));
        db.addContact(new Contact("Women", "Panties", "USA:XL,USA:12,Europe:44,UK:16,Italy:6,France:46,Spain:46,Australia:18,New Zealand:18,Japan:L,"));
        db.addContact(new Contact("Women", "Panties", "USA:1XL,USA:14,Europe:46,UK:18,Italy:7,France:48,Spain:48,Australia:20,New Zealand:20,Japan:XL,"));
        db.addContact(new Contact("Women", "Panties", "USA:2XL,USA:16-18,Europe:48,UK:20,Italy:8,France:50,Spain:50,Australia:22,New Zealand:22,Japan:XXL,"));
        db.addContact(new Contact("Women", "Panties", "USA:3XL,USA:18-20,Europe:50,UK:22,Italy:9,France:52,Spain:52,Australia:24,New Zealand:24,Japan:XXXL,"));

        db.addContact(new Contact("Women", "Pantyhose", "Europe:I (40-42),UK:S (Hip 34\"-36\" / Height 5'-5'4\"),USA:A (Up to 120 lbs / Height 5'0\" - 5'3\"),"));
        db.addContact(new Contact("Women", "Pantyhose", "Europe:II-III (42-44 / 44-46),UK:M (Hip 36\"-42\" / Height 5'2\"-5'7\"),USA:B (Up to 135 lbs / Height 5'3\" - 5'6\"),USA:C (Up to 150 lbs / Height 5'6\" - 5'8\"),"));
        db.addContact(new Contact("Women", "Pantyhose", "Europe:IV-V (48-50 / 50-52),UK:L (Hip 42\"-47\" / Height 5'5\"-5'10\"),USA:C (Up to 150 lbs / Height 5'6\" - 5'8\"),USA:D (Up to 165 lbs / Height 5'8\" and OVER),"));
        db.addContact(new Contact("Women", "Pantyhose", "Europe:VI (52-54),UK:XL (Hip 47\"-60\" / Height 5'10\"-6'),USA:Q (Up to 230 lbs),"));

        // Men
        db.addContact(new Contact("Men", "Tops", "USA:34,USA:XS,UK:34,Europe:44,Japan:XS,"));
        db.addContact(new Contact("Men", "Tops", "USA:36,USA:S,UK:36,Europe:46,Japan:S,"));
        db.addContact(new Contact("Men", "Tops", "USA:38,USA:M,UK:38,Europe:48,Japan:M,Japan:L,"));
        db.addContact(new Contact("Men", "Tops", "USA:40,USA:L,UK:40,Europe:50,Japan:L,"));
        db.addContact(new Contact("Men", "Tops", "USA:42,USA:L,UK:42,Europe:52,Japan:LL,"));
        db.addContact(new Contact("Men", "Tops", "USA:44,USA:XL,UK:44,Europe:54,Japan:LL,"));
        db.addContact(new Contact("Men", "Tops", "USA:46,USA:XXL,UK:46,Europe:56,Japan:LL,"));

        db.addContact(new Contact("Men", "Bottoms", "USA:XS,USA:28,UK:28,France:39,Europe:71,Japan:71,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:S,USA:29,UK:29,France:40,Europe:74,Japan:74,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:S,USA:30,UK:30,France:41,Europe:76,Japan:76,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:S,USA:31,UK:31,France:42,Europe:79,Japan:79,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:M,USA:32,UK:32,France:43,Europe:81,Japan:81,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:M,USA:33,UK:33,France:44,Europe:84,Japan:84,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:M,USA:34,UK:34,France:45,Europe:86,Japan:86,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:L,USA:35,UK:35,France:46,Europe:89,Japan:89,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:L,USA:36,UK:36,France:47,Europe:91,Japan:91,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:XL,USA:38,UK:38,France:48,Europe:97,Japan:97,"));
        db.addContact(new Contact("Men", "Bottoms", "USA:XL,USA:40,UK:40,France:50,Europe:102,Japan:102,"));

        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:34,UK:34,Europe:44,Japan:XS,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:36,UK:36,Europe:46,Japan:S,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:38,UK:38,Europe:48,Japan:M,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:40,UK:40,Europe:50,Japan:L,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:42,UK:42,Europe:52,Japan:L,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:44,UK:44,Europe:54,Japan:XL,"));
        db.addContact(new Contact("Men", "Suiting/Jackets", "USA:46,UK:46,Europe:56,Japan:XL,"));

        db.addContact(new Contact("Men", "Suiting/Pants", "USA:XS,UK:28,France:39,Europe:71,Japan:71,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:S,UK:29,France:40,Europe:74,Japan:74,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:S,UK:30,France:41,Europe:76,Japan:76,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:S,UK:31,France:42,Europe:79,Japan:79,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:M,UK:32,France:43,Europe:81,Japan:81,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:M,UK:33,France:44,Europe:84,Japan:84,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:M,UK:34,France:45,Europe:86,Japan:86,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:L,UK:35,France:46,Europe:89,Japan:89,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:L,UK:36,France:47,Europe:91,Japan:91,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:XL,UK:38,France:48,Europe:97,Japan:97,"));
        db.addContact(new Contact("Men", "Suiting/Pants", "USA:XL,UK:40,France:50,Europe:102,Japan:102,"));

        db.addContact(new Contact("Men", "Outerwear", "USA:XS,USA:34,UK:34,Europe:44,Japan:XS,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:S,USA:36,UK:36,Europe:46,Japan:S,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:M,USA:38,UK:38,Europe:48,Japan:M,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:M,USA:40,UK:40,Europe:50,Japan:L,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:L,USA:42,UK:42,Europe:52,Japan:L,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:XL,USA:44,UK:44,Europe:54,Japan:LL,"));
        db.addContact(new Contact("Men", "Outerwear", "USA:XL,USA:46,UK:46,Europe:56,Japan:LL,"));

        db.addContact(new Contact("Men", "Shoes", "USA:7,UK:6,Europe:40,Japan:26,"));
        db.addContact(new Contact("Men", "Shoes", "USA:7H,UK:6.5,Europe:40.5,Japan:26.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:8,UK:7,Europe:41,Japan:27,"));
        db.addContact(new Contact("Men", "Shoes", "USA:8H,UK:7.5,Europe:41.5,Japan:27.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:9,UK:8,Europe:42,Japan:28,"));
        db.addContact(new Contact("Men", "Shoes", "USA:9H,UK:8.5,Europe:42.5,Japan:28.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:10,UK:9,Europe:43,Japan:29,"));
        db.addContact(new Contact("Men", "Shoes", "USA:10H,UK:9.5,Europe:43.5,Japan:29.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:11,UK:10,Europe:44,Japan:30,"));
        db.addContact(new Contact("Men", "Shoes", "USA:11H,UK:10.5,Europe:44.5,Japan:30.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:12,UK:11,Europe:45,Japan:31,"));
        db.addContact(new Contact("Men", "Shoes", "USA:12H,UK:11.5,Europe:45.5,Japan:31.5,"));
        db.addContact(new Contact("Men", "Shoes", "USA:13,UK:12,Europe:46,Japan:32,"));
    }
}
