using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using BloodBankHCI_API.Models;

namespace BloodBankHCI_API.Controllers
{
    public class StanjaController : ApiController
    {
        private BankaKrviHCIEntities db = new BankaKrviHCIEntities();

        // GET: api/Stanja
        public IQueryable<Stanja> GetStanja()
        {
            return db.Stanja;
        }

        // GET 
        [HttpGet]
     //   [Route("api/Stanja/GetByCentarId/{centarId}")]
        public List<bsp_Stanja_SelectByCentarId_Result> GetStanjaByCentar(int centarId)
        {
            return db.bsp_Stanja_SelectByCentarId(centarId).ToList();
        }

        // GET: api/Stanja/5
        [ResponseType(typeof(Stanja))]
        public IHttpActionResult GetStanja(int id)
        {
            Stanja stanja = db.Stanja.Find(id);
            if (stanja == null)
            {
                return NotFound();
            }

            return Ok(stanja);
        }

        // PUT: api/Stanja/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutStanja(int id, Stanja stanja)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != stanja.StanjeId)
            {
                return BadRequest();
            }

            db.Entry(stanja).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!StanjaExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Stanja
        [ResponseType(typeof(Stanja))]
        public IHttpActionResult PostStanja(Stanja stanja)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Stanja.Add(stanja);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = stanja.StanjeId }, stanja);
        }

        // DELETE: api/Stanja/5
        [ResponseType(typeof(Stanja))]
        public IHttpActionResult DeleteStanja(int id)
        {
            Stanja stanja = db.Stanja.Find(id);
            if (stanja == null)
            {
                return NotFound();
            }

            db.Stanja.Remove(stanja);
            db.SaveChanges();

            return Ok(stanja);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool StanjaExists(int id)
        {
            return db.Stanja.Count(e => e.StanjeId == id) > 0;
        }
    }
}