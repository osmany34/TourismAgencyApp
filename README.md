# Turizm Acente Sistemi

Bu proje, Turizm Acentesi'nin günlük operasyonlarını dijital ortamda yönetmesini sağlamak amacıyla geliştirilmiştir. Sistem, JavaFX kullanılarak geliştirilmiş olup PostgreSQL veritabanı üzerinde çalışır.

## Veritabanı Yapısı

Sistem, aşağıdaki tabloları kullanır:

- **user**: Admin ve acente çalışanı (personel) kullanıcı bilgilerini tutar.
- **hotel**: Acentenin anlaşmalı olduğu otellerin bilgilerini içerir.
- **season**: Fiyatlandırmada kullanılacak dönemleri tanımlar.
- **pension**: Otellerde sunulan pansiyon tiplerini yönetir.
- **room**: Otellerdeki farklı oda tiplerini ve fiyatlandırmalarını içerir.
- **reservation**: Müşteri rezervasyonlarını takip eder.

## Kullanıcı Arayüzü (GUI)

Proje JavaFX ile geliştirilmiştir. Kullanıcı dostu ve işlevsel bir arayüz sağlar:

- **Admin Paneli**: Kullanıcı yönetimi (ekleme, silme, güncelleme) ve acentelerin listelenmesi.
- **Acente Çalışanı Paneli**: Otel yönetimi, oda yönetimi, rezervasyon işlemleri yapma.

## Kullanım

1. **Veritabanı Bağlantısı**: PostgreSQL veritabanını oluşturun ve bağlantı ayarlarını `DatabaseConfig.java` dosyasında güncelleyin.
2. **Proje Başlatma**: Proje, `Main.java` dosyasındaki `main` metoduyla başlatılabilir.
3. **Admin Girişi**: İlk olarak admin olarak giriş yapın ve kullanıcılar ekleyin.
4. **Acente Çalışanı Girişi**: Acente çalışanı olarak giriş yaparak otel yönetimi, oda yönetimi, rezervasyon işlemlerini gerçekleştirin.

## Kullanılan Teknolojiler 

- Java 19
- PostgreSQL veritabanı
- Intellij IDEA ide.
