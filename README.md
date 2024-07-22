EMLAK BURADA


![emlakBurada-Dml](https://github.com/user-attachments/assets/79dd594e-cb9d-4421-941d-fe79aaea3988)


POSTGRESQL = https://www.postman.com/dark-shadow-499458/workspace/emlakburadaws/collection/16881411-c99cdcc2-93c8-48ba-aa61-9bce39e07855?action=share&creator=16881411;


emlakBurada uygulaması kullanıcıların kendilerine ait  listing(yayın)‘lerini görebildikleri ve yönetebildikleri bir uygulamadır.
Bu uygulamada next.js , java Spring boot  postgresql ve rabbitMQ kullanılmıştır.

User yaratmak için bir arayüz bulunmamaktadır. Postman üzerinden yaratılabilir. User sonrası bir avatar fotoğrafı yaratmayı unutmayınız 
iki yapı birbirine bağımlı.Her user yaratıldığında o user’a ait haklar 0 olacak şekilde  bir UserBalance kaydı da atılır. 
Kullanıcı authService üzerinden uygulamaya auth olur. Bu yapı backend tarafında UserDetails’i extend ederek kurulmuş auth-service üzerinden yürür.
Front-end ‘de ise next auth ile login işlemleri düzenlenirken middleware yapısı ile route sadece authenticate  olmuş kullanıcılara  açık hale getirilmiş.
Authanticate olmayan user’ların erişimi engellenmiştir.Sistem’de login sonrası user-service ve purchase-payment-userbalance üçlüsünün yönettiği user sayfasına yönlendirme yapılır.
FrontEnd tarafında ilgili user bilgileri HOC olan withUser ile alınmıştır. Sayfa’da  kullanıcı bilgilerini almak üzere user service’ ine iki adet istekte bulunulur. 
Biri image biri gerekli detaylar içindir. Kullanıcı bu sayfada purchase butonuna bastığı anda purchase service’ine istekte bulunulur. Bu service önce payment service’i ile 
Feign Client üzerinden haberleşerek payment yapılması beklenir. Burada proje gereksinimi dışında olacağından payment sistemi kurulmamış sadece db’ve kayıt atılmıştır.
Db2den dönen PAID değeri ile birlikte purchase kaydı’nın db’ve kaydı ile async olacak şekilde bir UserBalanceUpdateRequest mesajı RabbitMQ üzerinden yollanır ver gerekli 
kayıtlar düzenlenir.Bütün bu süreçte hata olmadıysa kullanıcıya success mesajı döner.

Kullanıcı daha sonra Dashboard sayfasına gidebilir . Buraya navbar’da bulunan emlakBurada yazısından
ya da  dashboard simgesinden gidebilir . Sayfa ilk yüklenirken. PageWrap ile sarılarak hem gerekli listing bilgilerinin hem de page bilgilerinin response olarak döndüğü 
get isteğinde bulunulur. Burada yukarıdaki filtre yapısından listing service’ing o kullanıcıya ait farklı statü’deki değerlerin gelmesi beklenir. Kullanıcı cardların üzerinde 
bulunan silme düğmesinden listing silebilir. Yine listing service’ing yapılan bu istek önce bu listing’e ait image’i daha sonra listing kaydını siler. Ayrıca ok butonuna tıklayıp 
detaylı bilgilerin olduğu detay sayfasına ulaşır. Bu sayfa interaktif değildir. Sadece listing infosunu gösterir.  Kullanıcı çöp kutusunun yanındaki edit butonuna tıkladığında 
sayfada form’a bilgilerin geldiği edit sayfasına gider kullanıcı burada kendisine ait listingleri editleyebilir. Burada save’e basılınca toast notify ile kullanıcı success ya da 
error olarak bilgilendirilir. Tekrar dashboard’a dönersek add butonuna tıklanıldığında modal page açılır. Buradaki her alan value required olarak işaretlenmiştir. Kullanıcıdan 
tüm bilgileri eksizisz girmesi beklenir.Daha sonra save butonu basılır hem listing create için hem de image upload için gerekli istekler atılır. Modal success notification ile 
birlikte kapanır. Listing yaratılırken RabbitMQ’e bunun ile ilgili bir message iletilir. Listing’in IN-REVIEW olan status ‘ü async olarak ‘ACTIVE’’e çekilir. Kullanıcı navbar’daki 
logout() butonundan çıkış yapabilir.

Unit Test coverages
<img width="746" alt="Screen Shot 2024-07-22 at 09 23 09" src="https://github.com/user-attachments/assets/c9d9dddd-88b8-45ca-a2dc-9ee5979f4410">
<img width="766" alt="Screen Shot 2024-07-22 at 09 28 52" src="https://github.com/user-attachments/assets/86525c71-3e2c-40bb-a99a-70927e8fc6e8">
<img width="922" alt="Screen Shot 2024-07-22 at 09 26 41" src="https://github.com/user-attachments/assets/5c8b7b07-bb5f-42e8-83f6-64c0faa16915">
<img width="810" alt="Screen Shot 2024-07-22 at 09 24 50" src="https://github.com/user-attachments/assets/f3c642f4-7fa0-4052-b088-2f564a845810">
<img width="619" alt="Screen Shot 2024-07-22 at 09 24 14" src="https://github.com/user-attachments/assets/3e09bbfb-5029-4fa9-9a76-f44639cd25b8">
<img width="514" alt="Screen Shot 2024-07-22 at 09 23 45" src="https://github.com/user-attachments/assets/c70d9667-f319-489d-93b8-496106f392cc">

Son olarak bu bootcamp süresince bizimle değerli bilgi ve birimkilerini paylaşan hocalarıma teşekkürler.


