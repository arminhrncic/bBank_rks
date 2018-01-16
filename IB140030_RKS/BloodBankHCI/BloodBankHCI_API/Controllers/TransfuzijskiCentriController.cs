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
    public class TransfuzijskiCentriController : ApiController
    {
        private BankaKrviHCIEntities db = new BankaKrviHCIEntities();

        // GET: api/TransfuzijskiCentri
        public List<bsp_TransfuzijskiCentri_SelectAll_Result> GetTransfuzijskiCentri()
        {
            return db.bsp_TransfuzijskiCentri_SelectAll().ToList();
        }

        // GET: api/TransfuzijskiCentri/5
        [ResponseType(typeof(TransfuzijskiCentri))]
        public IHttpActionResult GetTransfuzijskiCentri(int id)
        {
            TransfuzijskiCentri transfuzijskiCentri = db.TransfuzijskiCentri.Find(id);
            if (transfuzijskiCentri == null)
            {
                return NotFound();
            }

            return Ok(transfuzijskiCentri);
        }

        // PUT: api/TransfuzijskiCentri/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutTransfuzijskiCentri(int id, TransfuzijskiCentri transfuzijskiCentri)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != transfuzijskiCentri.TransfuzijskiCentarId)
            {
                return BadRequest();
            }

            db.Entry(transfuzijskiCentri).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TransfuzijskiCentriExists(id))
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

        // POST: api/TransfuzijskiCentri
        [ResponseType(typeof(TransfuzijskiCentri))]
        public IHttpActionResult PostTransfuzijskiCentri(TransfuzijskiCentri transfuzijskiCentri)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.TransfuzijskiCentri.Add(transfuzijskiCentri);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = transfuzijskiCentri.TransfuzijskiCentarId }, transfuzijskiCentri);
        }

        // DELETE: api/TransfuzijskiCentri/5
        [ResponseType(typeof(TransfuzijskiCentri))]
        public IHttpActionResult DeleteTransfuzijskiCentri(int id)
        {
            TransfuzijskiCentri transfuzijskiCentri = db.TransfuzijskiCentri.Find(id);
            if (transfuzijskiCentri == null)
            {
                return NotFound();
            }

            db.TransfuzijskiCentri.Remove(transfuzijskiCentri);
            db.SaveChanges();

            return Ok(transfuzijskiCentri);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TransfuzijskiCentriExists(int id)
        {
            return db.TransfuzijskiCentri.Count(e => e.TransfuzijskiCentarId == id) > 0;
        }
    }
}