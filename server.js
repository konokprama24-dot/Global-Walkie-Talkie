const express = require('express');
const cors = require('cors');
const crypto = require('crypto');

const app = express();
app.use(cors());
app.use(express.json());

const channels = new Map();
const invitations = new Map();

app.get('/health', (_req, res) => res.json({ ok: true, service: 'Global Walkie Talkie API' }));

app.post('/api/channels', (req, res) => {
  const name = String(req.body.name || '').trim();
  if (!name) return res.status(400).json({ error: 'Channel name is required' });
  const id = crypto.randomUUID();
  const channel = { id, name, visibility: 'private', maxMembers: 1000, members: [], status: 'demo' };
  channels.set(id, channel);
  res.status(201).json(channel);
});

app.get('/api/channels/:id', (req, res) => {
  const channel = channels.get(req.params.id);
  if (!channel) return res.status(404).json({ error: 'Channel not found' });
  res.json(channel);
});

app.post('/api/channels/:id/join-requests', (req, res) => {
  const channel = channels.get(req.params.id);
  if (!channel) return res.status(404).json({ error: 'Channel not found' });
  const userId = String(req.body.userId || '').trim();
  if (!userId) return res.status(400).json({ error: 'userId is required' });
  res.status(202).json({ status: 'pending_admin_approval', channelId: channel.id, userId });
});

app.post('/api/invitations', (req, res) => {
  const channelId = String(req.body.channelId || '');
  if (!channels.has(channelId)) return res.status(404).json({ error: 'Channel not found' });
  const token = crypto.randomBytes(24).toString('hex');
  invitations.set(token, { channelId, expiresAt: Date.now() + 7 * 24 * 60 * 60 * 1000 });
  res.status(201).json({ invitePath: `/invite/${token}`, expiresInDays: 7 });
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`API listening on ${port}`));
