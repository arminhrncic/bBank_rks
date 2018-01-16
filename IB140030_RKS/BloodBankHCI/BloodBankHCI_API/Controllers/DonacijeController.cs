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
    public class DonacijeController : ApiController
    {
        private BankaKrviHCIEntities db = new BankaKrviHCIEntities();

        // GET: api/Donacije
        public IQueryable<Donacije> GetDonacije()
        {
            return db.Donacije;
        }



        [HttpGet]
       // [Route("api/Donacije/GetByDonatorId/{donatorId}")]
        public List<bsp_Donacije_GetByDonatorIdHCI_Result> GetDonacijeByDonator(int donatorId)
        {
            return db.bsp_Donacije_GetByDonatorIdHCI(donatorId).ToList();
        }

        // GET: api/Donacije/5


        // PUT: api/Donacije/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutDonacije(int id, Donacije donacije)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != donacije.DonacijaId)
            {
                return BadRequest();
            }

            db.Entry(donacije).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DonacijeExists(id))
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

        // POST: api/Donacije
        [ResponseType(typeof(Donacije))]
        public IHttpActionResult PostDonacije(Donacije donacije)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.bsp_Donacije_InsertHCI(donacije.TransfuzijskiCentarId, donacije.DonatorId, donacije.DatumDonacije,
                donacije.BrojDoza, donacije.Kolicina, donacije.UspjesnoRealizovana, donacije.Odbijena);

            return CreatedAtRoute("DefaultApi", new { id = donacije.DonacijaId }, donacije);
        }

        // DELETE: api/Donacije/5
        [ResponseType(typeof(Donacije))]
        public IHttpActionResult DeleteDonacije(int id)
        {
            Donacije donacije = db.Donacije.Find(id);
            if (donacije == null)
            {
                return NotFound();
            }

            db.Donacije.Remove(donacije);
            db.SaveChanges();

            return Ok(donacije);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool DonacijeExists(int id)
        {
            return db.Donacije.Count(e => e.DonacijaId == id) > 0;
        }
    }
}