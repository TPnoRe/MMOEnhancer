
## Configuration

Edit `config.yml` to customize:

- **Rarity Success Rates** - How likely enhancement succeeds
- **Boost Ranges** - How much stats increase on success
- **Enhancement Materials** - What item is required
- **Downgrade Amount** - How much stats decrease on failure

### Example Config

```yaml
rarity:
  LEGENDARY:
    success_rate: 20  # 20% success = 80% fail/downgrade
  EPIC:
    success_rate: 40
  RARE:
    success_rate: 60
  UNCOMMON:
    success_rate: 80
  COMMON:
    success_rate: 100  # Always succeeds
