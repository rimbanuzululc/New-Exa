/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  hp
 * Created: May 18, 2019
 */

--getAgentbyIdDebitur
select 
* 
from sli_agent where idDebitur = {{idDebitur}}

--listAgent
select
*
from sli_agent;

