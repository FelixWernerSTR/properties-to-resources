export class I[=mavenproject.entityName] {
  id?: number;
  angebotsnummer?: number | null;
  rahmenvertrag?: string | null;
  todesfallschutz?: string | null;
  anzahlVps?: string | null;
  schlagwort?: string | null;
  versicherungsbeginn?: string | Date | null;
  angebotsdatum?: string | null;
  buJN?: boolean | null;
}

export class Angebot implements I[=mavenproject.entityName] {
  constructor(
    public id?: number,
    public angebotsnummer?: number | null,
    public rahmenvertrag?: string | null,
    public todesfallschutz?: string | null,
    public anzahlVps?: string | null,
    public schlagwort?: string | null,
    public versicherungsbeginn?: string | null,
    public angebotsdatum?: string | null,
    public buJN?: boolean | null
  ) {}
}
