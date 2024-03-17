Feature: Update Query

  Background: Database connection
    * Database baglantisi kurulur.


  @UpdateQuery01
  Scenario:  "users" tablosunda sondan bir önceki harfi e olan
  "usernamelerin" "mobile" numarasını update ediniz.


    * UpdateQuery01 olusturulur. ve execute edilir.
    * UpdateQuery01 sonuclari islenir.
    * Database baglantisi kapatilir.

  @UpdateQuery02
  Scenario:    "users" tablosunda sondan bir önceki harfi e olan
  "usernamelerin" "mobile" numarasını update ediniz.

    * Prepared UpdateQuery01 olusturulur. ve execute edilir.
    * Prepared UpdateQuery01 sonuclari islenir.
    * Database baglantisi kapatilir.



  @InsertQuery03
  Scenario:   admin_password_resets tablosuna yeni (id,email,token,status,created_at datalarla)
  veri girisi yapiniz.


    * InsertQuery hazirlanir ve execute edilir
    * InsertQuery sonuclari dogrulanir
    * Database baglantisi kapatilir.

  @deleteQuery01
  Scenario: Update_logs tablosunda "id=?" değerine göre
  bir datayı siliniz ve silindiğini doğrulayınız.


    * Delete Query hazirlanir ve execute edilir
    * Datanin silindigi dogrulanir.
    * Database baglantisi kapatilir.

  @deleteQuery02
  Scenario:  "support_attachments" tablosunda "id = ?"
  değerine göre  bir dosyayı siliniz ve silindiğini doğrulayınız.


    # Bu sorguyu yapabilmek icin (ozellikle projede) once kendimize ait bir veri olusturup sonra onu silmeliyiz.


    * support_attachments tablosuna veri girilir.
    * support_attachments tablosuna eklenen veri silinir
    * Verinin silindigi dogrulanir.
    * Database baglantisi kapatilir.















