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
    public class ZahtjevZaKrvController : ApiController
    {
        private BankaKrviHCIEntities db = new BankaKrviHCIEntities();

        // GET: api/ZahtjevZaKrv
        public IQueryable<ZahtjevZaKrv> GetZahtjevZaKrv()
        {
            return db.ZahtjevZaKrv;
        }

        // GET: api/ZahtjevZaKrv/5
        [ResponseType(typeof(ZahtjevZaKrv))]
        public IHttpActionResult GetZahtjevZaKrv(int id)
        {
            ZahtjevZaKrv zahtjevZaKrv = db.ZahtjevZaKrv.Find(id);
            if (zahtjevZaKrv == null)
            {
                return NotFound();
            }

            return Ok(zahtjevZaKrv);
        }

        [HttpGet]
       // [Route("api/ZahtjevZaKrv/GetZahtjevZaKrvByDonatorId/{id}")]
        public IHttpActionResult GetZahtjevZaKrvByDonatorId(int id)
        {
            List<bsp_Zahtjevi_GetByDonatorId_Result> zahtjevZaKrv = db.bsp_Zahtjevi_GetByDonatorId(id).ToList();
            if (zahtjevZaKrv == null)
            {
                return NotFound();
            }

            return Ok(zahtjevZaKrv);
        }

        // PUT: api/ZahtjevZaKrv/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutZahtjevZaKrv(int id, ZahtjevZaKrv zahtjevZaKrv)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != zahtjevZaKrv.ZahtjevZaKrvId)
            {
                return BadRequest();
            }

            db.Entry(zahtjevZaKrv).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ZahtjevZaKrvExists(id))
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

        // POST: api/ZahtjevZaKrv
        [ResponseType(typeof(ZahtjevZaKrv))]
        public IHttpActionResult PostZahtjevZaKrv(ZahtjevZaKrv zahtjevZaKrv)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.bsp_Zahtjevi_InsertHCI(zahtjevZaKrv.TransfuzijskiCentarId, zahtjevZaKrv.DonatorId, zahtjevZaKrv.NazivKrvneGrupe,
                zahtjevZaKrv.BrojDoza, zahtjevZaKrv.DatumZahtjeva, zahtjevZaKrv.StatusZahtjevaId, zahtjevZaKrv.Kolicina);

            return CreatedAtRoute("DefaultApi", new { id = zahtjevZaKrv.ZahtjevZaKrvId }, zahtjevZaKrv);
        }

        // DELETE: api/ZahtjevZaKrv/5
        [ResponseType(typeof(ZahtjevZaKrv))]
        public IHttpActionResult DeleteZahtjevZaKrv(int id)
        {
            ZahtjevZaKrv zahtjevZaKrv = db.ZahtjevZaKrv.Find(id);
            if (zahtjevZaKrv == null)
            {
                return NotFound();
            }

            db.ZahtjevZaKrv.Remove(zahtjevZaKrv);
            db.SaveChanges();

            return Ok(zahtjevZaKrv);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ZahtjevZaKrvExists(int id)
        {
            return db.ZahtjevZaKrv.Count(e => e.ZahtjevZaKrvId == id) > 0;
        }
    }
}