/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Jan 30, 2021
 */

--reportVerifikasi
select 
ka.*,
sk.namadebitur,
sk.notelp as noDebitur,
sk.noAggrement 
from somasi_confirmkapos ka
left join somasi_konsumen sk on ka.konsumenid = sk.konsumenid 
where ka.useridkapos = {{id}}

--reportVerifikasiforAdmin
select 
sc.*,
sk.namadebitur,
sk.notelp as noDebitur,
sk.noAggrement 
from somasi_mappingareadminpos sm 
left join somasi_agentpos sa on sm.districtid = sa.districtid 
left join somasi_confirmkapos sc on sa.idagentpos = sc.idagentpos 
left join somasi_konsumen sk on sc.konsumenid = sk.konsumenid 
where sm.userid = {{userId}}

--reportAllVerifikasi
select 
sc.*,
sk.namadebitur,
sk.notelp as noDebitur,
sk.noAggrement 
from somasi_agentpos sa
left join somasi_confirmkapos sc on sa.idagentpos = sc.idagentpos 
left join somasi_konsumen sk on sc.konsumenid = sk.konsumenid 