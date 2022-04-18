/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: Aug 14, 2019
 */

--getByCodeSomasi1
select s.* from somasi_status1 s
where code = {{code}}

--getByCodeSomasi2
select s.* from somasi_status2 s
where code = {{code}}
