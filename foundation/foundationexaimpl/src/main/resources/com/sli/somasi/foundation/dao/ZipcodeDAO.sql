/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Jan 19, 2021
 */

--listByKecamatan
select
zipcodeid,
zipcode,
zipdescription,
kelurahancode,
kecamatancode,
citycode,
provinsicode
from somasi_zipcode where kecamatancode = '[[code]]'

